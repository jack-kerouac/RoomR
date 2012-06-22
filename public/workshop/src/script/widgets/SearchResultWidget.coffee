define ['base/RoomrWidget'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    setApplyButtonEvent: (element) ->
      $(".searchResultApplyForm").submit (event) =>
        event.preventDefault()
        console.log "Apply Button pressed: ", event
        console.log "hidden id = ", $("input[name=id]", event.target).val()
        enclosingLi = $(event.target).closest 'li'
        enclosingLi.addClass 'rotated'

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {searchResults: @searchResults}, (content) =>
        @nidus.html content
      @setApplyButtonEvent content

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
