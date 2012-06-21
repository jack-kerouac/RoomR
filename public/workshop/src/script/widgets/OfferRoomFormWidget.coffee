define ['base/RoomrWidget', 'base/renderTemplate', 'jquery-ui'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class OfferRoomFormWidget extends RoomrWidget

    constructor: ->
      super('offerRoomForm')

    renderInto: (element) ->
      @renderTemplate {}, (html) =>
        form = $(html)
        $(".appliance-icon", form).draggable {
          drag: (event, ui) ->
            $(this).draggable "option", "revert", true

          cursor: "move"
        }
        $(".appliance-stash", form).droppable {
          accept: ".appliance-icon"

          drop: (event, ui) ->
            ui.draggable.draggable "option", "revert", false
            $(ui.draggable).appendTo $(this)
            $(ui.draggable).css {
              top: 0
              left: 0
            }
        }

        $(element).append(form)