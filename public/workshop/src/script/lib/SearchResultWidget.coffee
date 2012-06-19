define ['lib/RoomrWidget'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    constructor: ->
      super('searchResult')

    renderInto: (element) ->
      @renderTemplate {}, (content) ->
        $(element).html content
