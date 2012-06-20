# Model für Einträge
# ------------------

# Einfach wieder ein Abziehbild der Basisklasse anlegen
define ['backbone'], (Backbone) ->  Backbone.Model.extend {

    url: -> @get('url')

}