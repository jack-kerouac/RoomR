define ['lib/EventEmitter'], (EventEmitter) ->
  'use strict'

  class LoginStateFinder extends EventEmitter
    constructor: ->
      super()
      @registerEvent 'loggedIn'
      @registerEvent 'loggedOut'

    findOutState: ->
      successCbk =  () => this.emit 'loggedIn'
      errorCbk = () => this.emit 'loggedOut'

      $.get('/rest/users/current', successCbk ).error errorCbk
