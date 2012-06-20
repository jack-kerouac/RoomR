# Router
# ------

# Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
# In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
# Änderungen im Page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
# Teile der Applikation reagieren
define ['backbone'], (Backbone) -> Backbone.Router.extend {

  initialize: (@nav) ->

  # Zuordnung von Routen und Funktionsnamen im Router
  routes: {
    ''          : 'start' # Startseite
    'about'     : 'about' # Info-Seite
    'login'     : 'login' # Login-Seite
    'view'      : 'view'  # Alle Einträge
    'view/:num' : 'view'  # Alle Einträge + Eintrag mit der Nummer `:num`
  }

  # Startseite
  start: ->
    @nav.showStart()

  # Info-Seite
  about: ->
    @nav.showAbout()

  # Zeigt den Login-Dialog. Der ist jetzt erst mal hier untergebracht um
  # "was zu sehen". Schlussendlich kommt der dann wo anders hin
  login: ->
    @nav.showLogin()

  # Alle Einträge auflisten und Ggf. den Eintrag mit der Nummer `:num` einblenden
  view: (num) ->
    if num?
      @nav.showSingleItem(Number num)
    else
      @nav.showItems()

  addSection: (section) ->
    @route section.path, section.name, -> section.render()

}