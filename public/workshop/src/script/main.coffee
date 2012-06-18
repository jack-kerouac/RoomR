# Fast die gesamte Beispielapp steckt in dieser Datei. Die aus den Kommentaren generierte
# Dokumentation liegt im `docs`-Verzeichnis. Die Kommentare können mit
# [Markdown](http://daringfireball.net/projects/markdown/) formatiert werden.


# Abhängigkeiten
# --------------

# Die `require()`-Funktion erwartet zwei Argumente: erstens ein Array mit den
# Abhängigkeiten für dieses Script und zweitens die Callback-Funktion, die ausgeführt
# wird, sobald alle Abhängigkeiten geladen wurden. Wenn die Abhängigkeiten selbst
# AMD-Module sind und Objekte zurückgeben, werden diese an den Callback als Argumente
# übergeben. Das ist nicht immer der Fall -- das `backbone`-Modul hat z.B. jQuery und
# [Underscrore.js](http://underscorejs.org/) Abhängigkeiten und lädt diese auch. Da
# je doch diese beiden Libraries  ihre APIs (`$` und `_`) als globale Objekte
# bereitstellen, können wir beides in unserer Callback-Funkion verwenden, ohne sie dort
# im Callback explizit aufzuführen.
require ['backbone', 'PageModel', 'PageView', 'ItemCollection', 'ItemView', 'ItemViewBig', 'AppRouter'], 
(Backbone, PageModel, PageView, ItemCollection, ItemView, ItemViewBig, AppRouter) ->
  
  'use strict'


  # Neue Instanz des Page-Models anlegen. Wenn wir die Page wechseln wollen, können wir
  # das Page.show(page, id) machen - den Job übernimmt der Router weiter unten.
  page = new PageModel()

  items = new ItemCollection()

  # Router anwerfen
  app = new AppRouter(page)



  # Pages-Model überwachen
  # ----------------------
  # Änderungen an der URL lösen also Änderungen am Pages-Model aus. Um auf diese zu
  # reagieren, hängen wir einfach ein paar Events an das Model an.

  openItem = null; # Welcher Eintrag ist geöffnet?

  page.on 'change:page', (model, value) ->
    switch value


      # Statische Startseite anzeigen. Wir laden einfach per jQuery die Template-Datei
      # `static/start.tpl.html` und geben ihren Inhalt an den Page-View weiter
      when 'start'
        $.get 'static/start.tpl.html', (result) ->
          if openItem? then openItem.remove()
          new PageView().render('Startseite', result)


      # About-Seite anzeigen. Template-Datei laden und den Inhalt als Template-String
      # verarbeiten. Der Platzhalter `<%= date %>` wird durch das aktuelle Datum ersetzt
      when 'about'
        $.get 'static/about.tpl.html', (tpl) ->
          content = _.template tpl, {
            date: Date().toString()
          }
          if openItem? then openItem.remove()
          new PageView().render('Über dieses Beispiel', content)


      # Einträge anzeigen. Wenn im Model darüber hinaus noch `num` nicht `undefined` ist,
      # müssen wir auch noch einen Einzel-Eintrag zeigen.
      #
      # 1. Template-Datei laden, in die Page rendern
      # 2. Items mittels `fetch()` vom Server holen
      # 3. Für jedes Item einen neuen `ItemView` in das Ziel-Element schreiben
      when 'view'
        $.get 'static/view.tpl.html', (result) ->
          new PageView().render('Einträge', result)
          items.fetch {  # `fetch()` wie einen ganz normalen jQuery-Request konfigurieren

            success: (collection) ->
              collection.each (listItem) ->  # `each()` ist eine Methode aus Underscore.js

                # Item-View-Instanz mit Konfiguation erstellen
                item = new ItemView {
                  tagName: 'li'     # Container-Tag (Standard: `div`)
                  model: listItem   # Model für diesen View
                }

                # Item-Events mit Event Delegation. In dem Event-Hash sind die Keys
                # Strings mit dem Event (hier: `click`) und dem Selektor, auf dem Event
                # aktiv werden soll (hier: `title`). Der Klick auf den Titel soll den
                # Eintrag in der Großansicht zeigen.
                item.delegateEvents {
                  'click .item-title': (evt) ->
                    id = $(evt.target).attr('id').split('-').pop()

                    # Der Status "offener Eintrag" soll auch in der URL abgebildet werden,
                    # daher lassen wir uns von dem Router an eine entsprechende Adresse
                    # schicken. Mit `{ trigger: no }` verhindern wir allerdings, dass
                    # diese Aktion die entsprechenden Events auf dem Router-Objekt
                    # auslöst - wir wollen ja nur einen großen Eintrag anzeigen und das
                    # können wir am besten direkt hier machen. **Tipp:** In CoffeeScript
                    # sind `no` und `off` das gleiche wie `false`. Entsprechend sind `yes`
                    # und `on` das gleiche wie `true`.
                    app.navigate "#view/#{id}"

                    # Ein großen Eintrag anzeigen ist ganz einfach: neue View-Instanz
                    # mit den passenden Events erstellen und das Ganze in den Body
                    # rendern. Vorher muss natürlich der ggf. schon geöffnete Eintrag
                    # geschlossen werden
                    if openItem? then openItem.remove()
                    openItem = new ItemViewBig {
                      model: items.at id
                      className: 'item-bigview'
                    }
                    openItem.delegateEvents {
                      'click .close-item': ->
                        @remove()  # Entfernt das Element des Views aus dem DOM
                        app.navigate "#view"
                    }
                    openItem.render 'body'

                }

                # Den Eintrag in die Liste hineinrendern.
                item.render('#ItemsList')

                # Falls das Page-Model `id` gesetzt hat und diese ID mit der Nummer
                # unseres gerade behandelten Eintrags übereinstimmt, müssen wir diesen
                # groß einblenden. Das machen wir einfach, indem wir ein Klick-Event auf
                # dem View triggern.
                if page.get('id') == item.model.get('id')
                  item.$el.find('.item-title').trigger('click')
          }


  # Das Page-Model auf Änderungen des `id`-Parameters überwachen
  page.on 'change:id', (model, id) ->

    # Wenn die ID nicht undefiniert/null ist, die View-Page angezeigt wird und die
    # Collection ein Item mit der ID enthält, dieses groß anzeigen. Vorher ggf. offene
    # Einträge schließen
    if id? && page.get('page') == 'view'
      item = items.find (model) ->
        return model.get('id') == id
      if item?
        if openItem? then openItem.remove()
        openItem = new ItemViewBig {
          model: item
          className: 'item-bigview'
        }
        openItem.delegateEvents {
          'click .close-item': ->
            @remove()
            app.navigate "#view"
        }
        openItem.render 'body'



  # App starten
  # -----------

  # Die URL auf Änderungen überwachen
  Backbone.history.start()