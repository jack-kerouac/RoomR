define ->
  'use strict'

  class EventMediator
    @events: {}
    @pendingSubscriptions: {}

    @validString: (string) ->
      string? and string != ""

    @registerEvent: (emitter, eventName) ->
      if eventName in @events
        throw new Error "An event of this name is already registered"
      if not @validString eventName
        throw new Error "Invalid name for event given"
      @events[eventName] = emitter

      pending = @pendingSubscriptions[eventName]
      if pending?
        pending.forEach (callback) -> emitter.subscribe eventName callback

    @subscribeToEvent: (eventName, callback) ->
      if not @validString eventName
        throw new Error "No eventName given when registering for event"

      if eventName in @events
        @events[eventName].subscribe eventName callback
      else
        if eventName not in @pendingSubscriptions
          @pendingSubscriptions[eventName] = []
        @pendingSubscriptions[eventName].push callback
