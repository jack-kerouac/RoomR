define ->
  'use strict'

  class EventEmitter
    constructor: ->
      @subscribers = {}
      @cachedProps = {}

    registerEvent: (eventName) ->
      @subscribers[eventName] = []
      window.roomr.eventMediator.registerEvent this, eventName

    registerPropChgEvent: (eventName) ->
      @cachedProps[eventName] = 'unknown'
      @registerEvent eventName

    subscribe: (eventName, callback) ->
      if eventName
        if @subscribers[eventName]?
          @subscribers[eventName].push(callback)
          if @cachedProps[eventName]?
            callback @cachedProps[eventName]
        else
          alert "Tried to subscribe to non-existing event" + eventName

    emit: (eventName, params...) ->
      if eventName
        if @subscribers[eventName]?
          if @cachedProps[eventName]?
            @cachedProps[eventName] = params[0]
          @subscribers[eventName].forEach (callback) ->
            callback.apply(this, params)
        else
          alert "Tried to emit unregistered event"

    listEvents: ->
      Object.keys @subscribers
