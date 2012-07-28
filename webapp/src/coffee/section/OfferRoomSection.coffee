define ['base/RoomrSection', 'widget/OfferRoomFormWidget'], (RoomrSection, OfferRoomFormWidget) ->
  'use strict'

  class OferRoomSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerRoom'
        title: 'Anzeige erstellen'
      }

      offerRoomFormWidget = new OfferRoomFormWidget()
      @addWidget offerRoomFormWidget
