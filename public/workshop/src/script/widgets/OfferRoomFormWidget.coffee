define ['base/RoomrWidget', 'base/renderTemplate', 'jquery-ui'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class OfferRoomFormWidget extends RoomrWidget

    constructor: ->
      super('offerRoomForm')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        form = $(html)

        $items = $("li", form)
        $items.draggable {
          drag: (event, ui) ->
            $(this).draggable "option", "revert", "invalid"
            $(this).removeClass 'dropped'

          cursor: "move"
        }
        $("ul", form).droppable {
          accept: $items

          drop: (event, ui) ->
            draggableOffset = $(ui.draggable).offset()
            thisOffset = $(this).offset()
            $(this).append $(ui.draggable)
        }

        $(element).append(form)