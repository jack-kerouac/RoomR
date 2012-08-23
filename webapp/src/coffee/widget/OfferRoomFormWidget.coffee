define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'model/roomOffer'], 
(Backbone, ko, kb, roomrUtil, roomOfferModel) ->
  'use strict'

  class OfferRoomWidget extends Backbone.View
    name: 'offerRoomForm'

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", 
      {'floorOptions' : roomOfferModel.floorOptions}, (html) =>
        @$el.append(html)
        ko.applyBindings kb.viewModel(roomOfferModel), @el

      console.log(roomOfferModel)

      roomOfferModel.on 'change', ->
        console.log(roomOfferModel)
        console.log('changed --- changed')
