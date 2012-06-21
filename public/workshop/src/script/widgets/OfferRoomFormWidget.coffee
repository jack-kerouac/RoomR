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

        $items = $("li", $content)
        $items.draggable {
          drag: (event, ui) ->
            $(this).draggable "option", "revert", "invalid"
            $(this).removeClass 'dropped'

          cursor: "move"
        }
        $("ul", $content).droppable {
          accept: $items

          drop: (event, ui) ->
            draggableOffset = $(ui.draggable).offset()
            thisOffset = $(this).offset()
            $(this).append $(ui.draggable)
        }

        $(element).append($content)

        # we expect to get the form as root element of the template
        $form = $content

        $form.submit (event) =>
          event.preventDefault()
          available = @getSelectedAppliances $form, 'available'
          nonAvailable = @getSelectedAppliances $form, 'non-available'
          true

