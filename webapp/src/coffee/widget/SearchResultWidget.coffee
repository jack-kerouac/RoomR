define ['backbone', 'base/roomrUtil', 'model/searchResults', 'base/mapsUtil'], (Backbone, roomrUtil, searchResults, mapsUtil) ->
  'use strict'

  class SearchResultWidget extends Backbone.View
    name: 'searchResult'

    initialize: ->
      searchResults.on 'change add remove reset', => @render()

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {'searchResults': searchResults.toJSON()}, (content) =>
        @$el.html content
        $("li", @el).each ->
          mapElem = $('.map', this)
          long = mapElem.data 'result-longitude'
          lat = mapElem.data 'result-latitude'
          console.log mapElem
          mapsUtil.whenMapsLoaded ->
            gmap = new GMaps {
              div: mapElem
              lat: lat
              lng: long
              zoom: 14
            }
            marker = gmap.addMarker {
              lat:lat
              lng:long
              title:'aktueller Standort'
            }

      return @


