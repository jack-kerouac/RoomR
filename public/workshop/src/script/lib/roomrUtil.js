(function() {

  define(function() {
    'use strict';
    return {
      addTypingFinishedCallback: function(elements, callback, doneTypingTimeout) {
        var targets, typingTimer;
        if (doneTypingTimeout == null) {
          doneTypingTimeout = 200;
        }
        targets = $(elements);
        typingTimer = {};
        return targets.each(function() {
          $(this).data('oldVal', $(this).val());
          return $(this).bind("propertychange keyup input paste", function() {
            var changed;
            changed = false;
            targets.each(function() {
              return changed = changed || $(this).data('oldVal') !== $(this).val();
            });
            if (changed) {
              targets.each(function() {
                return $(this).data('oldVal', $(this).val());
              });
              clearTimeout(typingTimer);
              return typingTimer = setTimeout(callback, doneTypingTimeout);
            }
          });
        });
      }
    };
  });

}).call(this);
