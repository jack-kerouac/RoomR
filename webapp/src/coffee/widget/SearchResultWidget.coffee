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
          mapElem = $('.static-map', this)
          long = mapElem.data 'result-longitude'
          lat = mapElem.data 'result-latitude'
          address = mapElem.data 'address'
          mapsUtil.whenMapsLoaded ->
            url = GMaps.staticMapURL({
              size: [mapElem.width(), mapElem.height()],
              lat: lat,
              lng: long,
              markers: [
                {lat: lat, lng: long}
              ]
            })
            mapElem.attr('src', url);

      return @


