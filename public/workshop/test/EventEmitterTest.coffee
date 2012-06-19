# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/lib/EventEmitter'], (EventEmitter) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul EventEmitter'

    # In`test()` finden die eigentlichen Tests statt
    test 'Funktion registerEvent_inEventEmitter_isAFunction()', ->
      emitter = new EventEmitter()
      ok typeof emitter.registerEvent == 'function'

    test 'Funktion listEvents_inEventEmitter_isAFunction()', ->
      emitter = new EventEmitter()
      ok typeof emitter.listEvents == 'function'

    test 'Funktion emit_inEventEmitter_isAFunction()', ->
      emitter = new EventEmitter()
      ok typeof emitter.emit == 'function'

    test 'Funktion subscribe_inEventEmitter_isAFunction()', ->
      emitter = new EventEmitter()
      ok typeof emitter.subscribe == 'function'

    test 'Funktion listEvents_afterRegisterEvent_containsThisEvent()', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo'
      ok 'foo' in emitter.listEvents()

    test 'Funktion listEvents_afterRegisterEventCalledOnce_containsOneEvent()', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo'

      equal emitter.listEvents().length, 1

    test 'Funktion emit_afterRegisterEvent_callsCallback()', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo'

      wasCalled = false
      fct = -> wasCalled = true

      emitter.subscribe 'foo', fct
      emitter.emit 'foo'

      ok wasCalled


    test 'Funktion emit_afterRegisterEvent_callsAllCallbacks()', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'bar'

      wasCalledOne = false
      wasCalledTwo = false
      fctOne = -> wasCalledOne = true
      fctTwo = -> wasCalledTwo = true

      emitter.subscribe 'bar', fctOne
      emitter.subscribe 'bar', fctTwo
      emitter.emit 'bar'

      ok wasCalledOne
      ok wasCalledTwo

    test 'Funktion emitWithParam_afterRegisterEvent_callsCallbackWithParam()', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'baz'

      eventParam = 0
      fct = (param) -> eventParam = param

      emitter.subscribe 'baz', fct
      emitter.emit 'baz', 42

      equal 42, eventParam
