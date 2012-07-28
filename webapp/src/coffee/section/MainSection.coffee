define ['base/RoomrSection', 'widget/SearchBoxWidget'], (RoomrSection, SearchBoxWidget) ->
  'use strict'

  class MainSection extends RoomrSection

    constructor: ->
      super {
        name: 'main'
        title: 'Hauptseite'
        path: ''
      }

      searchBoxWidget = new SearchBoxWidget()
      @addWidget searchBoxWidget
