define ['base/RoomrSection', 'widget/OfferRoomFormWidget'], (RoomrSection, OfferRoomFormWidget) ->
  'use strict'

  class OfferRoomSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerRoom'
        title: 'Anzeige erstellen'
      }
      

      offerRoomFormWidget = new OfferRoomFormWidget()
      @addWidget offerRoomFormWidget
