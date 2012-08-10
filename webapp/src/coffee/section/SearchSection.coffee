define ['base/RoomrSection', 'widget/SearchWidget', 'widget/SearchResultWidget','widget/MapWidget'], (RoomrSection, SearchWidget, SearchResultWidget, MapWidget) ->
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

      mapWidget = new MapWidget()
      @addWidget mapWidget

      searchWidget.search()