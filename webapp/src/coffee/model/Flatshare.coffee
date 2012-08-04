# Einfaches Datenobject, dass eine Anzeige hält
define ['backbone'], (Backbone) ->

  class Flatshare extends Backbone.Model
    urlRoot: '/rest/flatshares'
