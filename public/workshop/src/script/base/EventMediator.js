(function() {
  var __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  define(function() {
    'use strict';
    if (!(window.roomr != null)) {
      window.roomr = {};
    }
    return window.roomr.eventMediator = (function() {
      var events, pendingSubscriptions, validString;
      events = {};
      pendingSubscriptions = {};
      validString = function(string) {
        return (string != null) && string !== "";
      };
      return {
        registerEvent: function(emitter, eventName) {
          var pending;
          if (events[eventName] != null) {
            throw new Error("An event of this name is already registered");
          }
          if (!validString(eventName)) {
            throw new Error("Invalid name for event given");
          }
          events[eventName] = emitter;
          pending = pendingSubscriptions[eventName];
          if (pending != null) {
            return pending.forEach(function(callback) {
              return emitter.subscribe(eventName(callback));
            });
          }
        },
        subscribeToEvent: function(eventName, callback) {
          if (!validString(eventName)) {
            throw new Error("No eventName given when registering for event");
          }
          if (events[eventName] != null) {
            return events[eventName].subscribe(eventName, callback);
          } else {
            if (__indexOf.call(pendingSubscriptions, eventName) < 0) {
              pendingSubscriptions[eventName] = [];
            }
            return pendingSubscriptions[eventName].push(callback);
          }
        }
      };
    })();
  });

}).call(this);
