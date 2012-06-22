define ['base/RoomrWidget'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    installEventDelegator: (element) ->
      $(".searchResultContainer").each (index, element) ->
        addEventListener 'click', (event) =>
          event.preventDefault()
          target = event.explicitOriginalTarget
          targetName = event.explicitOriginalTarget.name
          if targetName?
            if targetName == 'apply'
              console.log "will rotate now ..."
              enclosingLi = $(target).closest 'li'
              enclosingLi.addClass 'rotated'
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
      @installEventDelegator content

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
