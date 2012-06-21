define ['base/RoomrSection', 'widgets/LoginWidget', 'widgets/OfferRoomFormWidget'], (RoomrSection, LoginWidget, OfferRoomFormWidget) ->
  'use strict'

  class OferRoomSection extends RoomrSection

    constructor: ->
      super {
        name: 'offerRoom'
        title: 'Anzeige erstellen'
      }

      loginWidget = new LoginWidget()
      @addWidget loginWidget
      
      offerRoomFormWidget = new OfferRoomFormWidget()
      @addWidget offerRoomFormWidget