  # Router
  # ------

  # Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
  # In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
  # Änderungen im Page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
  # Teile der Applikation reagieren
  define ['backbone'], (Backbone) -> Backbone.Router.extend {

    initialize: (page) -> @page = page

    # Zuordnung von Routen und Funktionsnamen im Router
    routes: {
      ''          : 'start' # Startseite
      'about'     : 'about' # Info-Seite
      'view'      : 'view'  # Alle Einträge
      'view/:num' : 'view'  # Alle Einträge + Eintrag mit der Nummer `:num`
    }

    # Startseite
    start: ->
      @page.show('start')

    # Info-Seite
    about: ->
      @page.show('about')

    # Alle Einträge auflisten und Ggf. den Eintrag mit der Nummer `:num` einblenden
    view: (num) ->
      @page.show('view', num)

  }

