define ->
  'use strict'
  {
    addTypingFinishedCallback: (elements, callback, doneTypingTimeout = 200) ->
      targets = $(elements)
      typingTimer = {}

      targets.each ->
        $(this).data 'oldVal', $(this).val()
        $(this).bind "propertychange keyup input paste", ->
          changed = false
          targets.each ->
            changed = changed || $(this).data('oldVal') != $(this).val()
            return true
          
          if (changed)
            targets.each ->
              $(this).data 'oldVal', $(this).val()
              return true

            clearTimeout typingTimer
            typingTimer = setTimeout callback, doneTypingTimeout

    addDropHandler: (element, callback) ->
      $(element).bind {
        # Drop-Operation ermöglichen
        dragover: (evt) ->
          evt.preventDefault()

        # Aktiv-Klasse bei Dropover
        dragenter: (evt) ->
          $(this).addClass 'active'
        dragleave: (evt) ->
          $(this).removeClass 'active'

        # Drop-Event
        drop: (evt) ->
          $(this).removeClass 'active'
          evt.preventDefault()
          callback.call(this, evt.originalEvent)
        }
  }