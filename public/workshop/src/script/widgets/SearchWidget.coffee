define ['backbone', 'base/RoomrWidget', 'base/roomrUtil', 'models/SearchResult'], 
(Backbone, RoomrWidget, roomrUtil, SearchResult) ->
  'use strict'

  class SearchWidget extends RoomrWidget
    SearchResultModel = Backbone.Model.extend {
      offer: -> @get('offer')
      matchingScore: -> @get('matchingScore')
    }

    SearchResultCollection = Backbone.Collection.extend {
      model: SearchResultModel
    }
    searchResults: new SearchResultCollection()

    constructor: ->
      super('search')
      @registerEvent 'searchResultsChanged'
      @registerGeolocation()
      @registerShakeEvent()

    performSearch: ->
      @searchResults.url = '/rest/roomOffers/search?' + $('#search_offers_form').serialize()
      @searchResults.fetch {
        success: (results) =>
          searchResultsArray = []
          completedSearchResults = 0
          results.each (result) =>
            searchResult = new SearchResult(result.offer(), result.matchingScore(), @lat, @long)
            searchResultsArray.push searchResult
          @emit 'searchResultsChanged', searchResultsArray
      }

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append(html)
        roomrUtil.addTypingFinishedCallback $(':input:not(:checkbox, :radio)', $(element)), => @performSearch()
        roomrUtil.addTypingFinishedCallback $(':checkbox, :radio', $(element)), => @performSearch()

    registerGeolocation: ->
      navigator.geolocation.getCurrentPosition (position) => 
          @lat = position.coords.latitude
          @long = position.coords.longitude

    registerShakeEvent: ->
      if window.DeviceMotionEvent?
        sensitivity = 20
        x1 = y1 = z1 = x2 = y2 = z2 = 0
        window.addEventListener 'devicemotion', (e) ->
          x1 = e.accelerationIncludingGravity.x
          y1 = e.accelerationIncludingGravity.y
          z1 = e.accelerationIncludingGravity.z
        , false
        setInterval ->
          change = Math.abs(x1-x2+y1-y2+z1-z2);

          if (change > sensitivity)
            $('#search_offers_form').each ->
              this.reset()

          x2 = x1;
          y2 = y1;
          z2 = z1;
        , 150