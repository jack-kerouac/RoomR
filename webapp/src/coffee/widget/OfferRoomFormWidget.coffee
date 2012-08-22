define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'model/roomOffer'], 
(Backbone, ko, kb, roomrUtil, roomOfferModel) ->
  'use strict'

  class OfferRoomWidget extends Backbone.View
    name: 'offerRoomForm'

    render: ->
      console.log('Offer Room widget rendered')
      roomrUtil.renderTemplate "widgets/#{this.name}", {}, (html) =>
        @$el.append(html)
        ko.applyBindings kb.viewModel(roomOfferModel), @el

      roomOfferModel.on 'change', ->
        console.log(roomOfferModel)
        console.log('changed --- changed')
