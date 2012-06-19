define ['lib/EventEmitter'], (EventEmitter) ->
  'use strict'

  class LoginStateFinder extends EventEmitter
    constructor: ->
      super()
      @registerEvent 'loggedIn'
      @registerEvent 'loggedOut'

    findOutState: ->
      successCbk =  () -> emit 'loggedIn'
      errorCbk = () -> emit 'loggedOut'

      $.get('/rest/users/current', successCbk ).error errorCbk
