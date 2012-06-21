(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/EventEmitter', 'base/renderTemplate'], function(EventEmitter, renderTemplate) {
    'use strict';

    var RoomrWidget;
    return RoomrWidget = (function(_super) {

      __extends(RoomrWidget, _super);

      function RoomrWidget(name) {
        this.name = name;
        RoomrWidget.__super__.constructor.call(this);
        if (!((this.name != null) || this.name === '')) {
          throw new Error('name is empty');
        }
      }

      RoomrWidget.prototype.name = '';

      RoomrWidget.prototype.renderTemplate = function(context, callback, name) {
        if (name != null) {
          return renderTemplate("widgets/" + name, context, callback);
        } else {
          return renderTemplate("widgets/" + this.name, context, callback);
        }
      };

      RoomrWidget.prototype.renderInto = function(element) {};

      RoomrWidget.prototype.subscribeToEvent = function(eventName, callback) {
        return window.roomr.eventMediator.subscribeToEvent(eventName, callback);
      };

      RoomrWidget.prototype.reportError = function(errorMsg) {
        return $(window).trigger('widgetError', [this.name, errorMsg]);
      };

      return RoomrWidget;

    })(EventEmitter);
  });

}).call(this);
