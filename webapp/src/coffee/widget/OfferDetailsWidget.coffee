define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'model/Offer'], 
(Backbone, ko, kb, roomrUtil, Offer) ->
  'use strict'

  class OfferDetailsWidget extends Backbone.View
    name: 'offerDetails'

    render: (id) ->
      offer = new Offer()
      offer.id = id
      
      offer.fetch().done =>
        console.log offer
        roomrUtil.renderTemplate "widgets/#{@name}", offer.toJSON(), (html) =>
          @$el.append(html)
