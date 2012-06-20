define ['base/renderTemplate', 'base/RoomrWidget'],
(renderTemplate, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget
    constructor: () ->
      super('login')
      @loginState = 'loggedOut'
      @registerEvent 'loginStateChanged'
      window.eventMediator.subscribeToEvent 'loginStateChanged', @onLoginStateChanged

    findOutState: ->
      successCbk = do => this.emit 'loginStateChanged', 'loggedIn'
      errorCbk = do => this.emit 'loginStateChanged', 'loggedOut'

      $.get('/rest/users/current', successCbk ).error errorCbk

    addEvents: ->
      $('#LoginWidgetForm').submit (event) =>
        event.preventDefault()
        username = $('#LoginWidgetForm input[name=login]').val()
        passwd = $('#LoginWidgetForm input[name=passwd]').val()
        postData = { email: username, password: passwd }
        $.ajax '/rest/login', {
          contentType : "application/json"
          data: JSON.stringify postData
          type: 'POST'
          complete: (jqXHR, stat) =>
            if stat == 'success'
              @emit 'loginStateChanged', 'loggedIn'
            else
              console.log "Kaputt", jqXHR
        }

    onLoginStateChanged: (newState) =>
      @loginState = newState
      @render()

    renderInto: (element) ->
      @elem = element
      @render()

    render: ->
      if @elem?
        if @loginState == 'loggedIn'
          @renderLoggedIn()
        else
          @renderLoggedOut()

    renderLoggedOut: () =>
      @name = 'login'
      @renderTemplate {}, (html) =>
          $(@elem).append(html)
          @addEvents()

    renderLoggedIn: () =>
      @name = 'profileInfo'
      @renderTemplate {}, (html) =>
          $(@elem).append(html)
