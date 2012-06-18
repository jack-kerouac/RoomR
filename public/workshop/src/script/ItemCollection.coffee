define ['backbone'], (Backbone) ->
  
  'use strict'
  
  # Model für Einträge
  # ------------------

  # Einfach wieder ein Abziehbild der Basisklasse anlegen
  Item = Backbone.Model.extend()



  # Collection für Einträge
  # -----------------------

  # Wenn wir Gruppen von Models verwalten wollen, sind **Collections** das Mittel der
  # Wahl.
  return Backbone.Collection.extend {
    model: Item  # Was für ein Model findet sich in dieser Collection?
    url: 'http://files.peterkroener.de/workshop/api/index.php/items/'  # Wo liegt die API?
  }
