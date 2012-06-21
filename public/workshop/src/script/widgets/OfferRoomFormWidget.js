(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrWidget', 'base/renderTemplate', 'jquery-ui'], function(RoomrWidget, renderTemplate) {
    'use strict';

    var OfferRoomFormWidget;
    return OfferRoomFormWidget = (function(_super) {

      __extends(OfferRoomFormWidget, _super);

      function OfferRoomFormWidget() {
        OfferRoomFormWidget.__super__.constructor.call(this, 'offerRoomForm');
      }

      OfferRoomFormWidget.prototype.getSelectedAppliances = function(form, type) {
        var appliances;
        appliances = _.map($("#appliances ." + type + " li", form), function(elem) {
          return $(elem).data('value');
        });
        return appliances;
      };

      OfferRoomFormWidget.prototype.renderInto = function(element) {
        var _this = this;
        return this.renderTemplate({}, function(content) {
          var $content, $form, $items;
          $content = $(content);
          $items = $("li", $content);
          $items.draggable({
            drag: function(event, ui) {
              $(this).draggable("option", "revert", true);
              return $(this).removeClass('dropped');
            },
            cursor: "move"
          });
          $("ul", $content).droppable({
            accept: $items,
            drop: function(event, ui) {
              ui.draggable.draggable("option", "revert", false);
              $(this).append($(ui.draggable));
              return $(ui.draggable).css({
                top: 0,
                left: 0
              });
            }
          });
          $(element).append($content);
          $form = $content;
          return $form.submit(function(event) {
            var available, nonAvailable;
            event.preventDefault();
            available = _this.getSelectedAppliances($form, 'available');
            nonAvailable = _this.getSelectedAppliances($form, 'non-available');
            return true;
          });
        });
      };

      return OfferRoomFormWidget;

    })(RoomrWidget);
  });

}).call(this);
