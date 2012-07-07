define ['base/RoomrWidget', 'base/renderTemplate', 'jquery-ui'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class OfferRoomFormWidget extends RoomrWidget

    constructor: ->
      super('offerRoomForm')

    getSelectedAppliances: (form, type) ->
      appliances = _.map $("#appliances .#{type} li", form), (elem) ->
        $(elem).data('value')
      appliances


    renderInto: (element) ->
      @renderTemplate {}, (content) =>
        $content = $(content)

        $items = $(".itemChoice li", $content)
        $items.draggable {
          drag: (event, ui) ->
            $(this).draggable "option", "revert", true
            $(this).removeClass 'dropped'

          cursor: "move"
        }
        $(".itemChoice ul", $content).droppable {
          accept: $items

          drop: (event, ui) ->
            ui.draggable.draggable "option", "revert", false
            $(this).append $(ui.draggable)
            $(ui.draggable).css {
              top: 0
              left: 0
            }
        }

        $(element).append($content)

        # we expect to get the form as root element of the template
        $form = $content

        $form.submit (event) =>
          event.preventDefault()
          available = @getSelectedAppliances $form, 'available'
          nonAvailable = @getSelectedAppliances $form, 'non-available'
          true

