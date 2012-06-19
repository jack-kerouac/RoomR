define ['backbone', 'User'], (Backbone, User) ->
  
  'use strict'
  
  # Collection für Einträge
  # -----------------------

  # Wenn wir Gruppen von Models verwalten wollen, sind **Collections** das Mittel der
  # Wahl.
  return Backbone.Collection.extend {
    model: User  # Was für ein Model findet sich in dieser Collection?
    url: 'rest/users'  # Wo liegt die API?
  }
