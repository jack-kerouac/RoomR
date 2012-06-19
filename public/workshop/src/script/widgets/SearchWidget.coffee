define ['backbone', 'base/RoomrWidget', 'base/roomrUtil', 'models/SearchResult'], 
(Backbone, RoomrWidget, roomrUtil, SearchResult) ->
  'use strict'

  class SearchWidget extends RoomrWidget
    SearchResultModel = Backbone.Model.extend {
      offer: -> @get('offer')
      matchingScore: -> @get('matchingScore')
    }

    SearchResultCollection = Backbone.Collection.extend {
      model: SearchResultModel  # Was für ein Model findet sich in dieser Collection?
    }
    searchResults: new SearchResultCollection()

    constructor: ->
      super('search')
      @registerEvent 'searchResultsChanged'

    performSearch: ->
      @searchResults.url = 'rest/roomOffers/search?' + $('#search_offers_form').serialize()
      @searchResults.fetch {
        success: (results) =>
          searchResultsArray = []
          results.each (result) ->
            searchResultsArray.push new SearchResult(result.offer, result.matchingScore)
          @emit 'searchResultsChanged', searchResultsArray
      }

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append(html)
        roomrUtil.addTypingFinishedCallback $(':input:not(:checkbox, :radio)', $(element)), => @performSearch()
        roomrUtil.addTypingFinishedCallback $(':checkbox, :radio', $(element)), => @performSearch()
