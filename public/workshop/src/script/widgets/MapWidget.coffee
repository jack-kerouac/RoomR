define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class MapWidget extends RoomrWidget

    searchResults: []

    nidus: undefined

    constructor: ->
      super('map')

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {}, (html) =>
        @nidus.html html
        @loadGoogle()
              
    loadGoogle: ->
      window.roomr = window.roomr || {}
      window.roomr.roomrMapWidgetDrawCallback = =>
        @loadGmaps()
      $.getScript 'http://maps.google.com/maps/api/js?sensor=false&callback=roomr.roomrMapWidgetDrawCallback'

    loadGmaps: ->
      $.getScript('src/script/vendor/gmaps.js', @createMap).fail (args...) -> console.log args

    createMap: ->      

      $(document).ready ->
        console.log "document ready"
        if navigator.geolocation
          console.log "geolocation"
          navigator.geolocation.getCurrentPosition (position)->
            console.log "set position"
            new GMaps {
              div: '#SearchResultMap',
              lat: position.coords.latitude,
              lng: position.coords.longitude,
              height: '400px'
            }
        else
          new GMaps {
            div: '#SearchResultMap',
            lat: '-12.043333',
            lng: '-77.028333',
            height: '400px'
          }



    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      