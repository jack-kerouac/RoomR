define ['base/RoomrSection', 'widgets/LoginWidget', 'widgets/CreateUserWidget'], (RoomrSection, LoginWidget, CreateUserWidget) ->
  'use strict'

  class MainSection extends RoomrSection

    constructor: ->
      super {
        name: 'main'
        title: 'Hauptseite'
        path: ''
      }

      loginWidget = new LoginWidget()
      @addWidget loginWidget

      createUserWidget = new CreateUserWidget()
      @addWidget createUserWidget

