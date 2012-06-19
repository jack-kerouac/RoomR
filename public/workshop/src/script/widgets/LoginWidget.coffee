define ['PageView', 'base/renderTemplate', 'base/EventMediator'], (PageView, renderTemplate, EventMediator) ->
  'use strict'

  class LoginWidget
    constructor: () ->
      EventMediator.subscribeToEvent 'loggedIn', @renderLoggedIn
      EventMediator.subscribeToEvent 'loggedOut', @renderLoggedOut

    render: () -> @renderLoggedOut()

    renderLoggedOut: () =>
      renderTemplate 'login', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('LOG DICH EIN!', content)

    renderLoggedIn: () =>
      renderTemplate 'profileInfo', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('Hi there', content)
