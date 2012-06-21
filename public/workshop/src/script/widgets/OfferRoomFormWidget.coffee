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
            $(this).draggable "option", "revert", true
            $(this).removeClass 'dropped'

          cursor: "move"
        }
        $("ul", form).droppable {
          accept: $items

          drop: (event, ui) ->
            ui.draggable.draggable "option", "revert", false
            $(this).append $(ui.draggable)
            $(ui.draggable).css {
              top: 0
              left: 0
            }
        }

        $(element).append(form)