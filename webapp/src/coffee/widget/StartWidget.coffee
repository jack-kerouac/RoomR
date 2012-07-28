define ['backbone', 'base/RoomrWidget'],
(Backbone, RoomrWidget) ->
  'use strict'

  class StartWidget extends RoomrWidget
    constructor: ->
      super('start')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append html
