// Generated by CoffeeScript 1.3.3
(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['backbone', 'base/RoomrWidget'], function(Backbone, RoomrWidget) {
    'use strict';

    var StartWidget;
    return StartWidget = (function(_super) {

      __extends(StartWidget, _super);

      function StartWidget() {
        StartWidget.__super__.constructor.call(this, 'start');
      }

      StartWidget.prototype.renderInto = function(element) {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          return $(element).append(html);
        });
      };

      return StartWidget;

    })(RoomrWidget);
  });

}).call(this);
