# Einfaches Datenobject, dass Suchergebnisse hält
define ['backbone'], (Backbone) ->
  class SearchResult extends Backbone.Model

    # doesn't quite work right now, but I leave it in because of the fancy formula...
    currentDistance: ->
      lat1 = @roomOffer.flatshare.geoLocation.latitude
      lon1 = @roomOffer.flatshare.geoLocation.longitude
      lat2 = @currentLat
      lon2 = @currentLong
      R = 6371
      dLat = (lat2-lat1) * Math.PI / 180
      dLon = (lon2-lon1) * Math.PI / 180
      lat1 = lat1 * Math.PI / 180
      lat2 = lat2 * Math.PI / 180

      a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2)
      c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
      d = R * c
      return Math.round(d)