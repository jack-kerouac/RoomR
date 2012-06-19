define ['PageView', 'UserView', 'lib/renderTemplate', 'lib/LoginWidget', 'lib/SearchWidget'], 
(PageView, UserView, renderTemplate, LoginWidget, SearchWidget) ->
	class Navigation
		
    users: null

    app: null

    constructor: (@users) ->
    
    showStart: ->
      renderTemplate 'start', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('Startseite', content)

    showAbout: -> 
      renderTemplate 'about', {date: Date().toString()}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('Über dieses Beispiel', content)

    showLogin: ->
      login = new LoginWidget()
      login.render('#Main')

    # Einträge anzeigen. Wenn im Model darüber hinaus noch `num` nicht `undefined` ist,
    # müssen wir auch noch einen Einzel-Eintrag zeigen.
    #
    # 1. Template-Datei laden, in die page rendern
    # 2. users mittels `fetch()` vom Server holen
    # 3. Für jedes User einen neuen `UserView` in das Ziel-Element schreiben
    showItems: ->
      renderTemplate 'view', {}, (content) =>
        new PageView().render('Einträge', content)
        @users.fetch {  # `fetch()` wie einen ganz normalen jQuery-Request konfigurieren

          success: (collection) =>
            collection.each (userItem) =>  # `each()` ist eine Methode aus Underscore.js

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
                'click .item-title': (evt) =>
                  id = $(evt.target).attr('id').split('-').pop()

                  # Der Status "offener Eintrag" soll auch in der URL abgebildet werden,
                  # daher lassen wir uns von dem Router an eine entsprechende Adresse
                  # schicken. Mit `{ trigger: no }` verhindern wir allerdings, dass
                  # diese Aktion die entsprechenden Events auf dem Router-Objekt
                  # auslöst - wir wollen ja nur einen großen Eintrag anzeigen und das
                  # können wir am besten direkt hier machen. **Tipp:** In CoffeeScript
                  # sind `no` und `off` das gleiche wie `false`. Entsprechend sind `yes`
                  # und `on` das gleiche wie `true`.
                  @app.navigate("#view/#{id}", on)
              }

              userItem.fetch()
              userItem.on 'change', ->
                # Den Eintrag in die Liste hineinrendern.
                itemView.render('#ItemsList')
        }

    showSingleItem: (id) ->
      # Wenn die ID nicht undefiniert/null ist, die View-Page angezeigt wird und die
      # Collection ein User mit der ID enthält, dieses groß anzeigen. Vorher ggf. offene
      # Einträge schließen
      if id? #&& page.get('page') == 'view'
        item = @users.find (model) ->
          return model.get('id') == id
        if item?
          if openItem? then openItem.remove()
          @openItem = new UserView {
            model: item
            className: 'item-bigview'
            template: 'full'
          }
          @openItem.delegateEvents {
            'click .close-item': ->
              @remove()
              app.navigate "#view"
          }
          @openItem.render 'body'
