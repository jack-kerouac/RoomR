define ['base/EventMediator', 'base/RoomrWidget'], (EventMediator, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget

  # TODO (Flo): how do we get the element to render this widget into when receiving events?

    constructor: () ->
      super('login')
      EventMediator.subscribeToEvent 'loggedIn', @renderLoggedIn
      EventMediator.subscribeToEvent 'loggedOut', @renderLoggedOut

    renderInto: (element) -> @renderLoggedOut(element)

    renderLoggedOut: (element) =>
      @renderTemplate {testVar: 'ABC'}, (content) ->
        if @openItem? then @openItem.remove()
        $(element).html(content)

    renderLoggedIn: (element) =>
      @renderTemplate {}, (content) ->
        if @openItem? then @openItem.remove()
        $(element).html(content)
