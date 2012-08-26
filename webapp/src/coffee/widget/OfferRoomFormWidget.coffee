define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'model/roomOffer'], 
(Backbone, ko, kb, roomrUtil, roomOfferModel) ->
  'use strict'

  class OfferRoomWidget extends Backbone.View
    name: 'offerRoomForm'

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", 
      {}, (html) =>
        @$el.append(html)
        ko.applyBindings kb.viewModel(roomOfferModel), @el
        submitButton = @$el.find('#offerRoomSubmitButton')
        console.log(submitButton)
        submitButton.click (e)->
          e.preventDefault()
          roomOfferModel.save()

      roomOfferModel.on 'change', ->
        console.log(roomOfferModel)
        console.log('changed --- changed')
