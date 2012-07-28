# This singleton model holds search queries
define ['backbone', 'model/searchResults'], (Backbone, searchResults) ->
  'use strict'

  class SearchQuery extends Backbone.Model
    defaults: {
      'minRoomSizeSquareMeters': '',
      'maxRentPerMonthInEuro': '',
      'startDateType': '',
      'startDate': '',
      'age': '',
      'gender': ''
    }
  searchQuery = new SearchQuery()

  searchTimer = undefined
  searchQuery.on 'change', ->
    clearTimeout searchTimer
    searchTimer = setTimeout searchCallback, 500

  searchCallback = ->
    params = {}
    _.each searchQuery.toJSON(), (value, key) ->
      params["searchData.#{key}"] = value
    searchResults.url = '/rest/roomOffers/search?' + $.param params
    searchResults.fetch()

  return searchQuery