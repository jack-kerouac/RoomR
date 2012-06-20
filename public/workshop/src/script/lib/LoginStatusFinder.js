(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['lib/EventEmitter'], function(EventEmitter) {
    'use strict';

    var LoginStateFinder;
    return LoginStateFinder = (function(_super) {

      __extends(LoginStateFinder, _super);

      function LoginStateFinder() {
        LoginStateFinder.__super__.constructor.call(this);
        this.registerEvent('loggedIn');
        this.registerEvent('loggedOut');
      }

      LoginStateFinder.prototype.findOutState = function() {
        var errorCbk, successCbk,
          _this = this;
        successCbk = function() {
          return _this.emit('loggedIn');
        };
        errorCbk = function() {
          return _this.emit('loggedOut');
        };
        return $.get('/rest/users/current', successCbk).error(errorCbk);
      };

      return LoginStateFinder;

    })(EventEmitter);
  });

}).call(this);
