define ['backbone', 'base/roomrUtil', 'models/SearchResult'], 
(Backbone, roomrUtil, SearchResult) ->
  'use strict'

  class SearchWidget extends Backbone.View
    name: 'search'

    SearchResultModel = Backbone.Model.extend {
      offer: -> @get('offer')
      matchingScore: -> @get('matchingScore')
    }

    SearchResultCollection = Backbone.Collection.extend {
      model: SearchResultModel
    }
    searchResults: new SearchResultCollection()

    performSearch: ->
      @searchResults.url = '/rest/roomOffers/search?' + $('#search_offers_form').serialize()
      @searchResults.fetch {
        success: (results) =>
          searchResultsArray = []
          completedSearchResults = 0
          results.each (result) =>
            searchResult = new SearchResult(result.offer(), result.matchingScore(), @lat, @long)
            searchResultsArray.push searchResult
          sortedResults = _.sortBy(searchResultsArray, (result) ->
            return result.currentDistance
          )
          @emit 'searchResultsChanged', sortedResults
      }

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {}, (html) =>
        @$el.append(html)
        roomrUtil.addTypingFinishedCallback $(':input:not(:checkbox, :radio)', @$el), => @performSearch()
        roomrUtil.addTypingFinishedCallback $(':checkbox, :radio', @$el), => @performSearch()