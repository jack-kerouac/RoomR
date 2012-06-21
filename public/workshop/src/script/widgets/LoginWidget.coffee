define ['base/renderTemplate', 'base/RoomrWidget'],
(renderTemplate, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget
    constructor: () ->
      super('login')
      @loginState = 'unknown'
      @userName = 'foo'
      @userEmail = 'foo'

      @registerPropChgEvent 'loginStateChanged'
      @registerPropChgEvent 'userNameChanged'
      @registerPropChgEvent 'userEmailChanged'
      @registerPropChgEvent 'userBirthdate'
      @registerPropChgEvent 'userGender'

      @subscribeToEvent 'loginStateChanged',
        (newState) => @loginState = newState; @render()
      @subscribeToEvent 'userNameChanged', (newName) => @userName = newName
      @subscribeToEvent 'userEmailChanged', (newEmail) => @userEmail = newEmail

      @findOutState()

    emitUserInfoEvents: (userInfo) ->
      @emit 'userNameChanged', userInfo.name
      @emit 'userEmailChanged', userInfo.email
      @emit 'userBirthdate', userInfo.birthdate
      @emit 'userGender', userInfo.gender
      @emit 'loginStateChanged', 'loggedIn'

    findOutState: ->
      $.ajax {
        url: '/rest/users/current'
        complete: (jqXHR, stat) =>
          if stat == 'success'
            @emitUserInfoEvents JSON.parse jqXHR.responseText
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
              userInfo = JSON.parse jqXHR.responseText
              if userInfo.error?
                alert "Fehler beim Login"
              else
                @emitUserInfoEvents userInfo
            else
              alert "Fehler beim Login"
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
              @emit 'userNameChanged', ''
              @emit 'userEmailChanged', ''
              @emit 'userBirthdate', ''
              @emit 'userGender', ''
              @emit 'loginStateChanged', 'loggedOut'
            else
              console.log "Kaputt", jqXHR
        }

    renderInto: (@elem) -> @render()

    render: ->
      if @elem?
        if @loginState == 'loggedIn'
          @renderLogoutForm()
        else
          @renderLoginForm()

    renderLoginForm: () =>
      @renderTemplate {}, (html) =>
        $(@elem).empty()
        $(@elem).append(html)
        @setLoginSubmitEvent()
      , 'login'

    renderLogoutForm: () =>
      @renderTemplate { UserName: @userName, UserEmail: @userEmail}, (html) =>
        $(@elem).empty()
        $(@elem).append(html)
        @setLogoutSubmitEvent()
      , 'logout'
