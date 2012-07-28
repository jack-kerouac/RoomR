define ['jquery'], ($) ->
  'use strict'

  mapsLoaded = $.Deferred()

  # GMaps can only be loaded once GoogleMaps has loaded completeley, thus this callback
  window.roomr = window.roomr || {}
  window.roomr.googleMapsLoaded = ->
    $.getScript('/scripts/gmaps.js', ->
      mapsLoaded.resolve()
    )
    delete window.roomr.googleMapsLoaded

  $.getScript('http://maps.google.com/maps/api/js?sensor=false&callback=roomr.googleMapsLoaded')

  return {
    whenMapsLoaded: (callback) ->
      mapsLoaded.done callback
  }