define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class MapWidget extends RoomrWidget

    nidus: undefined

    gmap: undefined  

    constructor: ->
      super('map')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {}, (html) =>
        @nidus.html html
        @loadGoogle()
              
    loadGoogle: ->
      window.roomr = window.roomr || {}
      window.roomr.roomrMapWidgetDrawCallback = @loadGmaps.bind(this)
      $.getScript 'http://maps.google.com/maps/api/js?sensor=false&callback=roomr.roomrMapWidgetDrawCallback'

    loadGmaps: ->
      $.getScript('src/script/vendor/gmaps.js', @renderMap.bind(this)).fail (args...) -> console.log args

    renderMap: ->      
      $(document).ready =>
        if navigator.geolocation
          navigator.geolocation.getCurrentPosition (position) =>
            @createMap position.coords.latitude,position.coords.longitude
        else
          @createMap '-12.043333','-77.028333'          

    createMap: (latitude, longitude) ->
      @gmap = new GMaps {
        div: '#SearchResultMap',
        lat: latitude,
        lng: longitude,
        height: '400px'
      }
      @addMarker latitude,longitude,'aktueller Standort'

    addMarker: (latitude, longitude, markertitle) ->
      @gmap.addMarker {lat:latitude, lng:longitude, title:markertitle}

    searchResultsChanged: (searchResults) ->
      for searchResult in searchResults
        lat = searchResult.roomOffer.flatshare.geoLocation.latitude
        long = searchResult.roomOffer.flatshare.geoLocation.longitude
        @addMarker lat,long,'title'
      
      