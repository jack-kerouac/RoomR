define ['base/RoomrSection', 'widgets/CreateUserWidget'], (RoomrSection, CreateUserWidget) ->
  'use strict'

  class MainSection extends RoomrSection

    constructor: ->
      super {
        name: 'main'
        title: 'Hauptseite'
        path: ''
      }

      createUserWidget = new CreateUserWidget()
      @addWidget createUserWidget

