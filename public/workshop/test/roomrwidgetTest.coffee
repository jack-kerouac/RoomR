# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/lib/roomrwidget'], (RoomrWidget) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul RoomrWidget'

    # In`test()` finden die eigentlichen Tests statt
    test 'Funktion registerEvent_inRoomrWidget_isAFunction()', ->
      emitter = new RoomrWidget()
      ok typeof emitter.registerEvent == 'function'

    test 'Funktion listEvents_inRoomrWidget_isAFunction()', ->
      emitter = new RoomrWidget()
      ok typeof emitter.listEvents == 'function'

    test 'Funktion emit_inRoomrWidget_isAFunction()', ->
      emitter = new RoomrWidget()
      ok typeof emitter.emit == 'function'

    test 'Funktion subscribe_inRoomrWidget_isAFunction()', ->
      emitter = new RoomrWidget()
      ok typeof emitter.subscribe == 'function'

    test 'Funktion listEvents_afterRegisterEvent_containsThisEvent()', ->
      emitter = new RoomrWidget()
      emitter.registerEvent 'foo'
      ok 'foo' in emitter.listEvents()

    test 'Funktion listEvents_afterRegisterEventCalledOnce_containsOneEvent()', ->
      emitter = new RoomrWidget()
      emitter.registerEvent 'foo'

      equal emitter.listEvents().length, 1

    test 'Funktion emit_afterRegisterEvent_callsCallback()', ->
      emitter = new RoomrWidget()
      emitter.registerEvent 'foo'

      wasCalled = false
      fct = -> wasCalled = true

      emitter.subscribe 'foo', fct
      emitter.emit 'foo'

      ok wasCalled

