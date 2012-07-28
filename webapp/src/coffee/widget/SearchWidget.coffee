define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'model/searchQuery', 'controller/searchController'], 
(Backbone, ko, kb, roomrUtil, searchQuery) ->
  'use strict'

  class SearchWidget extends Backbone.View
    name: 'search'

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {}, (html) =>
        @$el.append(html)
        ko.applyBindings kb.viewModel(searchQuery), @el
