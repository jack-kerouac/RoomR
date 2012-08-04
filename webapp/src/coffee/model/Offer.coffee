# Einfaches Datenobject, dass eine Anzeige hält
define ['backbone'], (Backbone) ->

  class Offer extends Backbone.Model
    urlRoot: '/rest/roomOffers'
