define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {searchResults: @searchResults}, (content) =>
        @nidus.html content

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
