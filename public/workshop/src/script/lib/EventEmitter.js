(function() {
  var __slice = [].slice;

  define(['lib/EventMediator'], function(EventMediatorX) {
    'use strict';

    var EventEmitter;
    return EventEmitter = (function() {

      function EventEmitter() {
        this.subscribers = {};
      }

      EventEmitter.prototype.registerEvent = function(eventName) {
        return this.subscribers[eventName] = [];
      };

      EventEmitter.prototype.subscribe = function(eventName, callback) {
        if (eventName) {
          if (this.subscribers[eventName] != null) {
            return this.subscribers[eventName].push(callback);
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
