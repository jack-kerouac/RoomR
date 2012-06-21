# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/base/EventMediator', '../src/script/base/EventEmitter'], (EventMediator, EventEmitter) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul EventEmitter'

    # In`test()` finden die eigentlichen Tests statt
    test 'Funktion registerEvent_inEventEmitter_isAFunction', ->
      emitter = new EventEmitter()
      ok typeof emitter.registerEvent == 'function'

    test 'Funktion listEvents_inEventEmitter_isAFunction', ->
      emitter = new EventEmitter()
      ok typeof emitter.listEvents == 'function'

    test 'Funktion emit_inEventEmitter_isAFunction', ->
      emitter = new EventEmitter()
      ok typeof emitter.emit == 'function'

    test 'Funktion subscribe_inEventEmitter_isAFunction', ->
      emitter = new EventEmitter()
      ok typeof emitter.subscribe == 'function'

    test 'Funktion listEvents_afterRegisterEvent_containsThisEvent', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo1'
      ok 'foo1' in emitter.listEvents()

    test 'Funktion listEvents_afterRegisterEventCalledOnce_containsOneEvent', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo2'

      equal emitter.listEvents().length, 1

    test 'Funktion emit_afterRegisterEvent_callsCallback', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'foo3'

      wasCalled = false
      fct = -> wasCalled = true

      emitter.subscribe 'foo3', fct
      emitter.emit 'foo3'

      ok wasCalled

    test 'Funktion emit_afterRegisterEvent_callsAllCallbacks', ->
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

    test 'Funktion emit_subscribeBeforeRegister_callsCallback', ->
      wasCalled = false
      fct = -> wasCalled = true

      emitter = new EventEmitter()
      window.roomr.eventMediator.subscribeToEvent 'lateRegister', fct
      emitter.registerEvent 'lateRegister'

      emitter.emit 'lateRegister'
      ok wasCalled

    test 'Funktion emitWithParam_afterRegisterEvent_callsCallbackWithParam', ->
      emitter = new EventEmitter()
      emitter.registerEvent 'baz'

      eventParam = 0
      fct = (param) -> eventParam = param

      emitter.subscribe 'baz', fct
      emitter.emit 'baz', 42

      equal 42, eventParam

    test 'Funktion subscribe_propChgEventDidntFired_callsCallbackWithUnknown', ->
      emitter = new EventEmitter()
      emitter.registerPropChgEvent 'propChanged1'

      wasCalled = false
      value = ''
      fct = (newValue) ->
        value = newValue
        wasCalled = true

      emitter.subscribe 'propChanged1', fct

      ok wasCalled
      equal value, 'unknown'

    test 'Funktion subscribe_andThenPropChgEventFires_callsCallCallbackWithProp', ->
      emitter = new EventEmitter()
      emitter.registerPropChgEvent 'propChanged2'

      wasCalled = false
      value = 0
      fct = (param) ->
        value = param
        wasCalled = true

      emitter.subscribe 'propChanged2', fct
      emitter.emit 'propChanged2', 23

      ok wasCalled
      equal value, 23

    test 'Funktion subscribe_afterPropChgEventFired_callsCallCallbackWithProp', ->
      emitter = new EventEmitter()
      emitter.registerPropChgEvent 'propChanged3'

      wasCalled = false
      value = 0
      fct = (param) ->
        value = param
        wasCalled = true

      # Note the difference to the later test: First emit, then subscribe!
      emitter.emit 'propChanged3', 1337
      emitter.subscribe 'propChanged3', fct

      ok wasCalled
      equal value, 1337
