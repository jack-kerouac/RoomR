define ['lib/RoomrWidget', 'lib/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined

    constructor: ->
      super('searchResult')

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {}, (content) =>
        @nidus.html content

    searchResultsChanged: (searchResults) ->
      _.each searchResults, (result) =>
        @nidus.append result.toString()
