define ['PageView', 'ItemView', 'ItemViewBig'], (PageView, ItemView, ItemViewBig) ->
	class Navigation
		
		#openItem: null

		#items: null

		#app: null

    constructor: (@openItem, @items, @app) ->
		
    showStart: ->
      $.get 'static/start.tpl.html', (result) ->
        if @openItem? then @openItem.remove()
        new PageView().render('Startseite', result)

    showAbout: -> 
      $.get 'static/about.tpl.html', (tpl) ->
        content = _.template tpl, {
          date: Date().toString()
        }
        if @openItem? then @openItem.remove()
        new PageView().render('Über dieses Beispiel', content)

    showItems: ->
      $.get 'static/view.tpl.html', (result) =>
        if @openItem? then @openItem.remove()
        new PageView().render('Einträge', result)
        
        @items.fetch {  # `fetch()` wie einen ganz normalen jQuery-Request konfigurieren

          success: (collection) =>
            collection.each (listItem) =>  # `each()` ist eine Methode aus Underscore.js

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

              # Den Eintrag in die Liste hineinrendern.
              item.render('#ItemsList')
        }

    showSingleItem: (id) ->
	    # Wenn die ID nicht undefiniert/null ist, die View-Page angezeigt wird und die
	    # Collection ein Item mit der ID enthält, dieses groß anzeigen. Vorher ggf. offene
	    # Einträge schließen
	    if id? #&& page.get('page') == 'view'
	      item = @items.find (model) ->
	        return model.get('id') == id
	      if item?
	        if @openItem? then @openItem.remove()
	        @openItem = new ItemViewBig {
	          model: item
	          className: 'item-bigview'
	        }
	        @openItem.delegateEvents {
	          'click .close-item': ->
	            @remove()
	            @app.navigate "#view"
	        }
	        @openItem.render 'body'
