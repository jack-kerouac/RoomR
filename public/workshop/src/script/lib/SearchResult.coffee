define ->
  class SearchResult
    constructor: (@offer, @matchingScore) ->
      throw new Error('offer is undefined!') unless @offer?
      throw new Error('matchingScore is undefined!') unless @matchingScore?