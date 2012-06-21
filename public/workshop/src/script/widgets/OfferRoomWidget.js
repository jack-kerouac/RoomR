(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrWidget', 'base/renderTemplate'], function(RoomrWidget, renderTemplate) {
    'use strict';

    var FlatshareWidget;
    return FlatshareWidget = (function(_super) {

      __extends(FlatshareWidget, _super);

      function FlatshareWidget() {
        FlatshareWidget.__super__.constructor.call(this, 'flatshare');
      }

      FlatshareWidget.prototype.renderInto = function(element) {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          return $(element).append(html);
        });
      };

      return FlatshareWidget;

    })(RoomrWidget);
  });

}).call(this);
