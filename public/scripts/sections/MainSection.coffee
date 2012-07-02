define ['base/RoomrSection.coffee#', 'widgets/StartWidget.coffee#'], (RoomrSection, StartWidget) ->
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

