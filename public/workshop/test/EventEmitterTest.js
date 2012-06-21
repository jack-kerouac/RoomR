(function() {
  var __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  require(['../src/script/base/EventMediator', '../src/script/base/EventEmitter'], function(EventMediator, EventEmitter) {
    'use strict';
    return $(document).ready(function() {
      module('Modul EventEmitter');
      test('Funktion registerEvent_inEventEmitter_isAFunction', function() {
        var emitter;
        emitter = new EventEmitter();
        return ok(typeof emitter.registerEvent === 'function');
      });
      test('Funktion listEvents_inEventEmitter_isAFunction', function() {
        var emitter;
        emitter = new EventEmitter();
        return ok(typeof emitter.listEvents === 'function');
      });
      test('Funktion emit_inEventEmitter_isAFunction', function() {
        var emitter;
        emitter = new EventEmitter();
        return ok(typeof emitter.emit === 'function');
      });
      test('Funktion subscribe_inEventEmitter_isAFunction', function() {
        var emitter;
        emitter = new EventEmitter();
        return ok(typeof emitter.subscribe === 'function');
      });
      test('Funktion listEvents_afterRegisterEvent_containsThisEvent', function() {
        var emitter;
        emitter = new EventEmitter();
        emitter.registerEvent('foo1');
        return ok(__indexOf.call(emitter.listEvents(), 'foo1') >= 0);
      });
      test('Funktion listEvents_afterRegisterEventCalledOnce_containsOneEvent', function() {
        var emitter;
        emitter = new EventEmitter();
        emitter.registerEvent('foo2');
        return equal(emitter.listEvents().length, 1);
      });
      test('Funktion emit_afterRegisterEvent_callsCallback', function() {
        var emitter, fct, wasCalled;
        emitter = new EventEmitter();
        emitter.registerEvent('foo3');
        wasCalled = false;
        fct = function() {
          return wasCalled = true;
        };
        emitter.subscribe('foo3', fct);
        emitter.emit('foo3');
        return ok(wasCalled);
      });
      test('Funktion emit_afterRegisterEvent_callsAllCallbacks', function() {
        var emitter, fctOne, fctTwo, wasCalledOne, wasCalledTwo;
        emitter = new EventEmitter();
        emitter.registerEvent('bar');
        wasCalledOne = false;
        wasCalledTwo = false;
        fctOne = function() {
          return wasCalledOne = true;
        };
        fctTwo = function() {
          return wasCalledTwo = true;
        };
        emitter.subscribe('bar', fctOne);
        emitter.subscribe('bar', fctTwo);
        emitter.emit('bar');
        ok(wasCalledOne);
        return ok(wasCalledTwo);
      });
      test('Funktion emitWithParam_afterRegisterEvent_callsCallbackWithParam', function() {
        var emitter, eventParam, fct;
        emitter = new EventEmitter();
        emitter.registerEvent('baz');
        eventParam = 0;
        fct = function(param) {
          return eventParam = param;
        };
        emitter.subscribe('baz', fct);
        emitter.emit('baz', 42);
        return equal(42, eventParam);
      });
      test('Funktion subscribe_propChgEventDidntFired_callsCallbackWithUnknown', function() {
        var emitter, fct, value, wasCalled;
        emitter = new EventEmitter();
        emitter.registerPropChgEvent('propChanged1');
        wasCalled = false;
        value = '';
        fct = function(newValue) {
          value = newValue;
          return wasCalled = true;
        };
        emitter.subscribe('propChanged1', fct);
        ok(wasCalled);
        return equal(value, 'unknown');
      });
      test('Funktion subscribe_andThenPropChgEventFires_callsCallCallbackWithProp', function() {
        var emitter, fct, value, wasCalled;
        emitter = new EventEmitter();
        emitter.registerPropChgEvent('propChanged2');
        wasCalled = false;
        value = 0;
        fct = function(param) {
          value = param;
          return wasCalled = true;
        };
        emitter.subscribe('propChanged2', fct);
        emitter.emit('propChanged2', 23);
        ok(wasCalled);
        return equal(value, 23);
      });
      return test('Funktion subscribe_afterPropChgEventFired_callsCallCallbackWithProp', function() {
        var emitter, fct, value, wasCalled;
        emitter = new EventEmitter();
        emitter.registerPropChgEvent('propChanged3');
        wasCalled = false;
        value = 0;
        fct = function(param) {
          value = param;
          return wasCalled = true;
        };
        emitter.emit('propChanged3', 1337);
        emitter.subscribe('propChanged3', fct);
        ok(wasCalled);
        return equal(value, 1337);
      });
    });
  });

}).call(this);
