define ->
  'use strict'

  if not window.room?
    window.roomr = {}

  window.roomr.eventMediator = do ->
    events = {}
    pendingSubscriptions = {}

    validString = (string) ->
      string? and string != ""

    return {
      registerEvent: (emitter, eventName) ->
        if events[eventName]?
          throw new Error "An event of this name is already registered"
        if not validString eventName
          throw new Error "Invalid name for event given"
        events[eventName] = emitter

        pending = pendingSubscriptions[eventName]
        if pending?
          pending.forEach (callback) -> emitter.subscribe eventName callback

      subscribeToEvent: (eventName, callback) ->
        if not validString eventName
          throw new Error "No eventName given when registering for event"

        if events[eventName]?
          events[eventName].subscribe eventName, callback
        else
          if eventName not in pendingSubscriptions
            pendingSubscriptions[eventName] = []
          pendingSubscriptions[eventName].push callback
    }
