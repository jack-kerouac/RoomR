# Einfaches Datenobject, dass Suchergebnisse hält
define ->
  class SearchResult
    constructor: (@roomOffer, @matchingScore) ->
      throw new Error('roomOffer is undefined!') unless @roomOffer?
      throw new Error('matchingScore is undefined!') unless @matchingScore?
