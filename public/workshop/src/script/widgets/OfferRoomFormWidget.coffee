define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class OfferRoomFormWidget extends RoomrWidget

    constructor: ->
      super('offerRoomForm')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append(html)