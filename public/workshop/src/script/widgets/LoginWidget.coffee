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

    setLoginSubmitEvent: ->
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
              console.log "Eingeloggt: ", jqXHR
            else
              console.log "Kaputt", jqXHR
        }

    setLogoutSubmitEvent: ->
      $('#LogoutWidgetForm').submit (event) =>
        event.preventDefault()
        postData = { msg : "I'm off" }
        $.ajax '/rest/logout', {
          contentType : "application/json"
          data: JSON.stringify postData
          type: 'POST'
          complete: (jqXHR, stat) =>
            if stat == 'success'
              @emit 'loginStateChanged', 'loggedOut'
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
          @renderLogoutForm()
        else
          @renderLoginForm()

    renderLoginForm: () =>
      @name = 'login'
      @renderTemplate {}, (html) =>
          $(@elem).empty()
          $(@elem).append(html)
          @setLoginSubmitEvent()

    renderLogoutForm: () =>
      @name = 'logout'
      @renderTemplate {}, (html) =>
          $(@elem).empty()
          $(@elem).append(html)
          @setLogoutSubmitEvent()
          @fetchAndFillProfileInfo()
