define ['base/EventEmitter'], (EventEmitter) ->
  'use strict'

  class LoginStateFinder extends EventEmitter
    constructor: ->
      super()

    findOutState: ->
      console.log "Weg damit"
