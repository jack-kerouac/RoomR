define ['backbone', 'base/roomrUtil'], (Backbone, roomrUtil) ->
  'use strict'

  class SearchResultWidget extends Backbone.View
    name: 'searchResult'

    searchResults: []

    events: {
      'click': (element) ->
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
    }

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {searchResults: @searchResults}, (content) =>
        @$el.html content
      return @

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @render
