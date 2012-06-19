define ['PageView', 'lib/renderTemplate', 'lib/EventEmitter'], (PageView, renderTemplate, EventEmitter) ->
  'use strict'

  class LoginWidget extends EventEmitter
    constructor: ->
      super()
      @registerEvent 'onLogin'
      @registerEvent 'onLogout'

    isLoggedIn: ->
      successCbk =  () -> alert "Sind eingeloggt"
      errorCbk = () -> alert "Sind nicht eingeloggt"

      $.get('/rest/users/current', successCbk ).error errorCbk

    render: (idOfParentElement) ->
      if @isLoggedIn()
        @renderProfileInfo(idOfParentElement)
      else
        @renderLoginWidget(idOfParentElement)

    renderProfileInfo: (idOfParentElement) ->
      # TODO: Implement this
      @renderLoginWidget(idOfParentElement)

    renderLoginWidget: (idOfParentElement) ->
      renderTemplate 'login', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('LOG DICH EIN!', content)
