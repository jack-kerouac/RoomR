define ['base/RoomrWidget'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {searchResults: @searchResults}, (content) =>
        @nidus.html content

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
