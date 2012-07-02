define ['base/RoomrSection.coffee#', 'widgets/OfferRoomFormWidget.coffee#'], (RoomrSection, OfferRoomFormWidget) ->
  'use strict'

  class OferRoomSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerRoom'
        title: 'Anzeige erstellen'
      }

      offerRoomFormWidget = new OfferRoomFormWidget()
      @addWidget offerRoomFormWidget
