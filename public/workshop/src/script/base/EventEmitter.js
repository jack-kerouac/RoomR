(function() {
  var __slice = [].slice;

  define(function() {
    'use strict';

    var EventEmitter;
    return EventEmitter = (function() {

      function EventEmitter() {
        this.subscribers = {};
        this.cachedProps = {};
      }

      EventEmitter.prototype.registerEvent = function(eventName) {
        this.subscribers[eventName] = [];
        return window.roomr.eventMediator.registerEvent(this, eventName);
      };

      EventEmitter.prototype.registerPropChgEvent = function(eventName) {
        this.cachedProps[eventName] = 'unknown';
        return this.registerEvent(eventName);
      };

      EventEmitter.prototype.subscribe = function(eventName, callback) {
        if (eventName) {
          if (this.subscribers[eventName] != null) {
            this.subscribers[eventName].push(callback);
            if (this.cachedProps[eventName] != null) {
              return callback(this.cachedProps[eventName]);
            }
          } else {
            return alert("Tried to subscribe to non-existing event" + eventName);
          }
        }
      };

      EventEmitter.prototype.emit = function() {
        var eventName, params;
        eventName = arguments[0], params = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
        if (eventName) {
          if (this.subscribers[eventName] != null) {
            if (this.cachedProps[eventName] != null) {
              this.cachedProps[eventName] = params[0];
            }
            return this.subscribers[eventName].forEach(function(callback) {
              return callback.apply(this, params);
            });
          } else {
            return alert("Tried to emit unregistered event");
          }
        }
      };

      EventEmitter.prototype.listEvents = function() {
        return Object.keys(this.subscribers);
      };

      return EventEmitter;

    })();
  });

}).call(this);
