define ['backbone', 'knockout', 'knockback', 'base/roomrUtil', 'base/mapsUtil', 'model/Offer'], 
(Backbone, ko, kb, roomrUtil, mapsUtil, Offer) ->
  'use strict'

  class OfferDetailsWidget extends Backbone.View
    name: 'offerDetails'

    gmap: undefined

    render: (id) ->
      offer = new Offer()
      offer.id = id
      
      offer.fetch().done =>
        console.log offer
        
        # fetch flatshare
        fs = offer.flatshare()
        fs.fetch().done =>
          offerAttr = offer.toJSON()

          console.log fs

          roomrUtil.renderTemplate "widgets/#{@name}", {
              roomDetails: offerAttr.roomDetails
              flatshare: fs.toJSON()
              criteria: offerAttr.criteria
            },
            (html) =>
              @$el.append(html)
              mapsUtil.whenMapsLoaded =>
                geo = fs.get 'geoLocation'
                @createMap geo.latitude, geo.longitude

    createMap: (lat, long) ->
      @gmap = new GMaps {
        div: '.map',
        lat: lat,
        lng: long,
        zoom: 13,
      }
      marker = @gmap.createMarker {lat:lat, lng:long, title:'WG'}
      marker.setMap(@gmap.map);
      @gmap.markers.push(marker);

