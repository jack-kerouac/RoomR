(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrWidget', 'base/renderTemplate'], function(RoomrWidget, renderTemplate) {
    'use strict';

    var OfferRoomFormWidget;
    return OfferRoomFormWidget = (function(_super) {

      __extends(OfferRoomFormWidget, _super);

      function OfferRoomFormWidget() {
        OfferRoomFormWidget.__super__.constructor.call(this, 'offerRoomForm');
      }

      OfferRoomFormWidget.prototype.renderInto = function(element) {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          return $(element).append(html);
        });
      };

      return OfferRoomFormWidget;

    })(RoomrWidget);
  });

}).call(this);
