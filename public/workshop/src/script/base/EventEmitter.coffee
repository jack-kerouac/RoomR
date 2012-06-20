define ->
  'use strict'

  class EventEmitter
    constructor: ->
      @subscribers = {}

    registerEvent: (eventName) ->
      @subscribers[eventName] = []
      window.eventMediator.registerEvent this, eventName

    subscribe: (eventName, callback) ->
      if eventName
        if @subscribers[eventName]?
          @subscribers[eventName].push(callback)
        else
          alert "Tried to subscribe to non-existing event" + eventName

    emit: (eventName, params...) ->
      if eventName
        if @subscribers[eventName]?
          @subscribers[eventName].forEach (callback) ->
            callback.apply(this, params)
        else
          alert "Tried to emit unregistered event"

    listEvents: ->
      Object.keys @subscribers
