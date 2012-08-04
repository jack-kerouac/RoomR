# Einfaches Datenobject, dass eine Anzeige hält
define ['backbone', 'model/Flatshare'], (Backbone, Flatshare) ->

  class Offer extends Backbone.Model
    urlRoot: '/rest/roomOffers'
    flatshare: ->
      url = @get "flatshare"
      flatshare = new Flatshare()
      flatshare.id = 1
      flatshare
