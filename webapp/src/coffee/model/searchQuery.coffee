# This singleton model holds search queries
define ['backbone'], (Backbone) ->
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
  return searchQuery