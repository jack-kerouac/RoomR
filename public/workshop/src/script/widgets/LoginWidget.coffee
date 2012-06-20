define ['PageView', 'base/renderTemplate', 'base/RoomrWidget'], (PageView, renderTemplate, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget
    constructor: () ->
      super('login')
      window.eventMediator.subscribeToEvent 'loggedIn', @renderLoggedIn
      window.eventMediator.subscribeToEvent 'loggedOut', @renderLoggedOut

    renderInto: (element) ->
      @elem = element

    renderLoggedOut: () =>
      renderTemplate 'login', {}, (content) ->
        if @openItem? then @openItem.remove()
        renderTemplate {}, (html) =>
          $(@elem).append(html)

    renderLoggedIn: () =>
      renderTemplate 'profileInfo', {}, (content) ->
        if @openItem? then @openItem.remove()
        renderTemplate {}, (html) =>
          $(@elem).append(html)
