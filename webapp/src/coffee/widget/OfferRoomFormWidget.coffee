define ['backbone', 'knockout', 'knockback', 'base/roomrUtil'], 
(Backbone, ko, kb, roomrUtil) ->
  'use strict'

  class OfferRoomWidget extends Backbone.View
    name: 'offerRoomForm'

    render: ->
      console.log('Offer Room widget rendered')
      # roomrUtil.renderTemplate "widgets/#{this.name}", {}, (html) =>
      #   @$el.append(html)
      #   ko.applyBindings kb.viewModel(searchQuery), @el
