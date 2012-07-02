define ['base/RoomrWidget.coffee#'], (RoomrWidget) ->
  'use strict'

  class SearchResultWidget extends RoomrWidget
    nidus: undefined
    searchResults: []

    constructor: ->
      super('searchResult')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params
      @registerEvent 'drawRoute'

    installEventDelegator: (element) ->
      $(".searchResultContainer").each (index, element) ->
        addEventListener 'click', (event) =>
          event.preventDefault()
          target = event.explicitOriginalTarget
          targetName = event.explicitOriginalTarget.name
          if targetName?
            if targetName == 'apply'
              enclosingLi = $(target).closest 'li'
              enclosingLi.addClass 'rotated'
            if targetName == 'sendApply'
              console.log "sending apply. Good luck..."
              alert "Not yet implemented. Sorry for that"
            if targetName == 'saveDraft'
              console.log "saving text as draft..."
              alert "Not yet implemented. Sorry for that"
            if targetName == 'turnBack'
              enclosingLi = $(target).closest 'li'
              enclosingLi.removeClass 'rotated'

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {searchResults: @searchResults}, (content) =>
        @nidus.html content
        @installEventDelegator content
      $(document).ready =>
        widget = this
        $('.DrawRouteButton').bind 'click', ->
          #console.log 'click button. id=' + $(this).data('index')
          widget.emit 'drawRoute', $(this).data('index')

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @renderInto @nidus
