define ['base/RoomrWidget'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    installEventDelegators: (element) ->
      $(".searchResultContainer").each (index, element) ->
        addEventListener 'click', (event) =>
          event.preventDefault()
          targetName = event.explicitOriginalTarget.name
          if targetName?
            if targetName == 'apply'
              console.log "will rotate now ..."
            if targetName == 'sendApply'
              console.log "sending apply. Good luck..."
            if targetName == 'saveDraft'
              console.log "saving text as draft..."
            if targetName == 'discard'
              console.log "forgetting text..."
        #console.log "hidden id = ", $("input[name=id]", event.target).val()
        #enclosingLi = $(event.target).closest 'li'
        #enclosingLi.addClass 'rotated'

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {searchResults: @searchResults}, (content) =>
        @nidus.html content
      @installEventDelegators content

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
