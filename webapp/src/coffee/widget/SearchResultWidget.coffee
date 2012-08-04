define ['backbone', 'base/roomrUtil', 'model/searchResults'], (Backbone, roomrUtil, searchResults) ->
  'use strict'

  class SearchResultWidget extends Backbone.View
    name: 'searchResult'

    initialize: ->
      searchResults.on 'change add remove reset', => @render()

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {'searchResults': searchResults.toJSON()}, (content) =>
        @$el.html content
      return @
