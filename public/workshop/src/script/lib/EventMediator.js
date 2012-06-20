(function() {
  var __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  define(function() {
    'use strict';

    var EventMediator;
    return EventMediator = (function() {

      function EventMediator() {}

      EventMediator.events = {};

      EventMediator.pendingSubscriptions = {};

      EventMediator.validString = function(string) {
        return (string != null) && string !== "";
      };

      EventMediator.registerEvent = function(emitter, eventName) {
        var pending;
        if (__indexOf.call(this.events, eventName) >= 0) {
          throw new Error("An event of this name is already registered");
        }
        if (!this.validString(eventName)) {
          throw new Error("Invalid name for event given");
        }
        this.events[eventName] = emitter;
        pending = this.pendingSubscriptions[eventName];
        if (pending != null) {
          return pending.forEach(function(callback) {
            return emitter.subscribe(eventName(callback));
          });
        }
      };

      EventMediator.subscribeToEvent = function(eventName, callback) {
        if (!this.validString(eventName)) {
          throw new Error("No eventName given when registering for event");
        }
        if (__indexOf.call(this.events, eventName) >= 0) {
          return this.events[eventName].subscribe(eventName(callback));
        } else {
          if (__indexOf.call(this.pendingSubscriptions, eventName) < 0) {
            this.pendingSubscriptions[eventName] = [];
          }
          return this.pendingSubscriptions[eventName].push(callback);
        }
      };

      return EventMediator;

    })();
  });

}).call(this);
