define ['base/RoomrSection', 'widget/OfferDetailsWidget'], (RoomrSection, OfferDetailsWidget) ->
  'use strict'

  class OfferDetailsSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerDetails'
        title: 'WG-Anzeige'
        path: 'offers/:id'
      }

      offerDetailsWidget = new OfferDetailsWidget()
      @addWidget offerDetailsWidget
