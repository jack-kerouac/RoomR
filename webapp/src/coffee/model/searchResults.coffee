# This singleton model holds search results
define ['backbone', 'model/SearchResult'], (Backbone, SearchResult) ->
  SearchResults = Backbone.Collection.extend {
    model: SearchResult
  }
  searchResults = new SearchResults()
  return searchResults