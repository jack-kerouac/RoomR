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
  }