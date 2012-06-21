define ['base/RoomrSection', 'widgets/SearchWidget', 'widgets/SearchResultWidget'], (RoomrSection, SearchWidget, SearchResultWidget) ->
  'use strict'

  class SearchSection extends RoomrSection

    constructor: ->
      super {
        name: 'search'
        title: 'Suche'
      }

      searchWidget = new SearchWidget()
      @addWidget searchWidget

      searchResultWidget = new SearchResultWidget()
      @addWidget searchResultWidget