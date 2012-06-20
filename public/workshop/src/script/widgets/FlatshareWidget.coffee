define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class FlatshareWidget extends RoomrWidget

    constructor: ->
      super('flatshare')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        $(element).append(html)