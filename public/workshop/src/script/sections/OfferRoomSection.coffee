define ['base/RoomrSection', 'widgets/OfferRoomFormWidget'], (RoomrSection, OfferRoomFormWidget) ->
  'use strict'

  class OferRoomSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerRoom'
        title: 'Anzeige erstellen'
      }

      offerRoomFormWidget = new OfferRoomFormWidget()
      @addWidget offerRoomFormWidget
