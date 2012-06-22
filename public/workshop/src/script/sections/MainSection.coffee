define ['base/RoomrSection', 'widgets/StartWidget'], (RoomrSection, StartWidget) ->
  'use strict'

  class MainSection extends RoomrSection

    constructor: ->
      super {
        name: 'main'
        title: 'Hauptseite'
        path: ''
      }

      startWidget = new StartWidget()
      @addWidget startWidget

