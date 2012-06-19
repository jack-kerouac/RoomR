define ['PageView', 'lib/renderTemplate', 'lib/eventEmitter'], (PageView, renderTemplate, eventEmitter) ->
  'use strict'

  class loginWidget extends eventEmitter
    constructor: ->
      registerEvent 'onLogin'
      registerEvent 'onLogout'

    isLoggedIn: ->
      # TODO Implement this
      false

    render: (idOfParentElement) ->
      if isLoggedIn()
        renderProfileInfo(idOfParentElement)
      else
        renderLoginWidget(idOfParentElement)

    renderProfileInfo: (idOfParentElement) ->
      # TODO: Implement this
      renderLoginWidget(idOfParentElement)

    renderLoginWidget: (idOfParentElement) ->
      renderTemplate 'login', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('LOG DICH EIN!', content)
