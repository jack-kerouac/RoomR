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
require ['backbone', 'lib/renderTemplate'], (Backbone, renderTemplate) ->
  'use strict'


  # Model für Pages
  # ---------------

  # Als `page` wird in diesem Beispiel der Inhalt der Hauptspalte bezeichnet. Ein
  # **Model** hierfür erstellen wir, indem wir von der Model-Vorlage `Backbone.Model`
  # über die **`extend()`-Methode** ein Abziehbild erstellen und unsere eigenen
  # Eigenschaften hineinschreiben. Wir können einerseits vorhandene Eigenschaften und
  # Methoden überschreiben (z.B. `initialize`), andererseits auch komplett eigene
  # Eigenschaften festlegen (z.B. `show`).
  PageModel = Backbone.Model.extend {

    # Wir überschreiben die normale (nichts machende) `initialize()`-Methode, um zu Beginn
    # immer `start` als page zu verwenden
    initialize: ->
      # **Tipp:** `@` ist CoffeeScript das gleiche wie `this.`
      @set {
        page: 'start'
      }

    # Eine eigene Methode, die nichts weiter macht als auf komfortable Art und Weise die
    # Werte `page` und `id` zu setzen. Bei `id` aufpassen, dass es eine Number ist - der
    # Parameter kommt als String aus der URL an
    show: (page, id) ->
      @set {
        page: page
        id: Number id
      }

  }

  # Neue Instanz des page-Models anlegen. Wenn wir die page wechseln wollen, können wir
  # das page.show(page, id) machen - den Job übernimmt der Router weiter unten.
  page = new PageModel()



  # View für Pages
  # --------------

  # Views sind für den Output von Daten verantwortlich. Man kann sie direkt mit Models
  # verzahnen, muss das aber nicht tun. Eigentlich *muss* man gar nichts - Views sind sehr
  # flexibel und es gibt nicht viele Vorgaben. Dieser PageView dient dazu, den Inhalt der
  # Hauptspalte `#main` neu zu rendern.
  PageView = Backbone.View.extend {

    # Jeder View repräsentiert ein HTML-Element. Das kann entweder ein bereits vorhandenes
    # Element in der Seite sein oder es kann kann auch erst vom View erzeugt werden. In
    # diesem Fall ist das Element für diesen View (View-Eigenschaft `el`) ein Element in
    # der Seite - daher der Selektor hier.
    el: '#Main'

    # `render()` ist die View-Methode für die Ausgabe. Standardmäßig macht `render()` gar
    # nichts, d.h. man *muss* für jeden View eine eigene Methode schreiben. Diese Variante
    # nimmt einen Titel und Inhalt an und befüllt damit die entsprechenden Elemente.
    render: (title, html) ->
      $('#Headline').html title                   # Überschrift setzen
      # **Tipp:** In Strings mit " gibt es String-Interpolation mit `#{variable}`
      $('title').text("Beispiel-App - #{title}")  # `<title>` setzen
      # **Tipp:** `$el` in Views ist ein jQuery-Objekt mit dem `el` des Views

      @$el.html(html)             # Das Element des Views mit Inhalt befüllen
      return this

  }


  # Model für Einträge
  # ------------------

  # Einfach wieder ein Abziehbild der Basisklasse anlegen
  User = Backbone.Model.extend {
    url: -> @get('url')
  }



  # Collection für Einträge
  # -----------------------

  # Wenn wir Gruppen von Models verwalten wollen, sind **Collections** das Mittel der
  # Wahl.
  UserCollection = Backbone.Collection.extend {
    model: User  # Was für ein Model findet sich in dieser Collection?
    url: 'rest/users'  # Wo liegt die API?
  }
  users = new UserCollection()



  # View für Einträge
  # -----------------

  #
  UserView = Backbone.View.extend {
    # Da Einträge könnten recht vielfältig verwendet werden können, richten wir uns auf
    # flexible Nutzung von Templates ein.
    initialize: (options) ->
      @template = options.template

    # Für das Rendering verwenden wir das übergebene Template und hängen das fertige
    # Element direkt in `target` ein.
    render: (target) ->
      target = $(target) unless target instanceof jQuery
      context = {
        id: @model.get('id')
        email: @model.get('email')
        name: @model.get('name')
      }
      renderTemplate "user/#{@template}", context, (html) =>
        @$el.html(html).appendTo(target)

      return this
  }



  # Router
  # ------

  # Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
  # In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
  # Änderungen im page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
  # Teile der Applikation reagieren
  AppRouter = Backbone.Router.extend {

    # Zuordnung von Routen und Funktionsnamen im Router
    routes: {
      ''          : 'start' # Startseite
      'about'     : 'about' # Info-Seite
      'view'      : 'view'  # Alle Einträge
      'view/:num' : 'view'  # Alle Einträge + Eintrag mit der Nummer `:num`
    }

    # Startseite
    start: ->
      page.show('start')

    # Info-Seite
    about: ->
      page.show('about')

    # Alle Einträge auflisten und Ggf. den Eintrag mit der Nummer `:num` einblenden
    view: (num) ->
      page.show('view', num)

  }

  # Router anwerfen
  app = new AppRouter()



  # Pages-Model überwachen
  # ----------------------
  # Änderungen an der URL lösen also Änderungen am Pages-Model aus. Um auf diese zu
  # reagieren, hängen wir einfach ein paar Events an das Model an.

  openItem = null; # Welcher Eintrag ist geöffnet?

  page.on 'change:page', (model, value) ->
    switch value


      # Statische Startseite anzeigen. Wir laden einfach per jQuery die Template-Datei
      # `static/start.tpl.html` und geben ihren Inhalt an den page-View weiter
      when 'start'
        renderTemplate 'start', {}, (content) ->
          if openItem? then openItem.remove()
          new PageView().render('Startseite', content)


      # About-Seite anzeigen. Template-Datei laden und den Inhalt als Template-String
      # verarbeiten. Der Platzhalter `<%= date %>` wird durch das aktuelle Datum ersetzt
      when 'about'
        renderTemplate 'about', {date: Date().toString()}, (content) ->
          if openItem? then openItem.remove()
          new PageView().render('Über dieses Beispiel', content)


      # Einträge anzeigen. Wenn im Model darüber hinaus noch `num` nicht `undefined` ist,
      # müssen wir auch noch einen Einzel-Eintrag zeigen.
      #
      # 1. Template-Datei laden, in die page rendern
      # 2. users mittels `fetch()` vom Server holen
      # 3. Für jedes User einen neuen `UserView` in das Ziel-Element schreiben
      when 'view'
        renderTemplate 'view', {}, (content) ->
          new PageView().render('Einträge', content)
          users.fetch {  # `fetch()` wie einen ganz normalen jQuery-Request konfigurieren

            success: (collection) ->
              collection.each (userItem) ->  # `each()` ist eine Methode aus Underscore.js

                # User-View-Instanz mit Konfiguation erstellen
                itemView = new UserView {
                  tagName: 'li'     # Container-Tag (Standard: `div`)
                  model: userItem   # Model für diesen View
                  template: 'list'  # Template-Vorlage oder -String
                }

                # User-Events mit Event Delegation. In dem Event-Hash sind die Keys
                # Strings mit dem Event (hier: `click`) und dem Selektor, auf dem Event
                # aktiv werden soll (hier: `title`). Der Klick auf den Titel soll den
                # Eintrag in der Großansicht zeigen.
                itemView.delegateEvents {
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
                    openItem = new UserView {
                      model: users.find (user) ->
                        return user.get('id') == Number id
                      className: 'item-bigview'
                      template: 'full'
                    }
                    openItem.delegateEvents {
                      'click .close-item': ->
                        @remove()  # Entfernt das Element des Views aus dem DOM
                        app.navigate "#view"
                    }
                    openItem.render 'body'
                }

                userItem.fetch()
                userItem.on 'change', ->
                  # Den Eintrag in die Liste hineinrendern.
                  itemView.render('#ItemsList')

                # Falls das page-Model `id` gesetzt hat und diese ID mit der Nummer
                # unseres gerade behandelten Eintrags übereinstimmt, müssen wir diesen
                # groß einblenden. Das machen wir einfach, indem wir ein Klick-Event auf
                # dem View triggern.
                if page.get('id') == itemView.model.get('id')
                  itemView.$el.find('.item-title').trigger('click')
          }


  # Das page-Model auf Änderungen des `id`-Parameters überwachen
  page.on 'change:id', (model, id) ->

    # Wenn die ID nicht undefiniert/null ist, die View-page angezeigt wird und die
    # Collection ein User mit der ID enthält, dieses groß anzeigen. Vorher ggf. offene
    # Einträge schließen
    if id? && page.get('page') == 'view'
      item = users.find (model) ->
        return model.get('id') == id
      if item?
        if openItem? then openItem.remove()
        openItem = new UserView {
          model: item
          className: 'item-bigview'
          template: 'full'
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