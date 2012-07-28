# This singleton controller actually performs the search as soon as the query changes
define ['model/searchQuery', 'model/searchResults'], (searchQuery, searchResults) ->
  searchQuery.on 'change', ->
    # TODO: change names of parameters such that Play understands them...
    params = {}
    _.each searchQuery.toJSON(), (value, key) ->
      params["searchData.#{key}"] = value
    searchResults.url = '/rest/roomOffers/search?' + $.param params
    searchResults.fetch()

  return undefined