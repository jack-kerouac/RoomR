# This singleton controller actually performs the search as soon as the query changes
define ['models/SearchQuery'], (Backbone) ->
  class SearchQuery extends Backbone.Model
    

  searchQuery = new SearchQuery()
  return searchQuery