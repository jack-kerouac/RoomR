# Router
# ------

# Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
# In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
# Änderungen im Page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
# Teile der Applikation reagieren
define ['backbone'], (Backbone) -> Backbone.Router.extend {

  # TODO: Flo: handle unexpected hash tags
  addSection: (section) ->
    @route section.path, section.name, -> section.render()

}