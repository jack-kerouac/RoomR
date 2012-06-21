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
              changed = changed || $(this).data('oldVal') !== $(this).val();
              return true;
            });
            if (changed) {
              targets.each(function() {
                $(this).data('oldVal', $(this).val());
                return true;
              });
              clearTimeout(typingTimer);
              return typingTimer = setTimeout(callback, doneTypingTimeout);
            }
          });
        });
      },
      addDropHandler: function(element, callback) {
        return $(element).bind({
          dragover: function(evt) {
            return evt.preventDefault();
          },
          dragenter: function(evt) {
            return $(this).addClass('active');
          },
          dragleave: function(evt) {
            return $(this).removeClass('active');
          },
          drop: function(evt) {
            $(this).removeClass('active');
            evt.preventDefault();
            return callback.call(this, evt.originalEvent);
          }
        });
      }
    };
  });

}).call(this);
