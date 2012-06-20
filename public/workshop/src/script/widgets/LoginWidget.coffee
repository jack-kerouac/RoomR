define ['base/renderTemplate', 'base/RoomrWidget'],
(renderTemplate, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget
    constructor: () ->
      super('login')
      @loginState = 'unknown'
      @registerPropChgEvent 'loginStateChanged'
      window.eventMediator.subscribeToEvent 'loginStateChanged', @onLoginStateChanged
      @findOutState()

    findOutState: ->
      $.ajax {
        url: '/rest/users/current'
        complete: (jqXHR, stat) =>
          if stat == 'success'
            @emit 'loginStateChanged', 'loggedIn'
          else
            @emit 'loginStateChanged', 'loggedOut'
      }

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
          $(@elem).empty()
          $(@elem).append(html)
          @addEvents()

    renderLoggedIn: () =>
      @name = 'profileInfo'
      @renderTemplate {}, (html) =>
          $(@elem).empty()
          $(@elem).append(html)
