define ['lib/backbone', 'base/RoomrWidget.coffee#'],
(Backbone, RoomrWidget) ->
  'use strict'

  class StartWidget extends RoomrWidget
    constructor: ->
      super('start')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append html
