define ['base/RoomrSection', 'widgets/CreateUserWidget'], (RoomrSection, CreateUserWidget) ->
  'use strict'

  class RegisterSection extends RoomrSection

    constructor: ->
      super {
        name: 'register'
        title: 'Registrieren'
      }

      createUserWidget = new CreateUserWidget()
      @addWidget createUserWidget

