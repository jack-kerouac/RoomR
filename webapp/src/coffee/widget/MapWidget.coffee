define ['backbone', 'base/mapsUtil', 'base/roomrUtil', 'model/searchResults'], \
        (Backbone,  mapsUtil,        roomrUtil,        searchResults) ->
  'use strict'

  class MapWidget extends Backbone.View
    name: 'map'
    
    gmap: undefined  

    currentLat: 48.139126
    currentLong: 11.580186

    initialize: ->
      searchResults.on 'change add remove reset', =>
        mapsUtil.whenMapsLoaded =>
          @drawSearchResults()

    render: ->
      roomrUtil.renderTemplate "widgets/#{this.name}", {}, (html) =>
        @$el.html html
        mapsUtil.whenMapsLoaded =>
          @createMap()

    createMap: ->
      @gmap = new GMaps {
        div: '#search-result-map',
        lat: @currentLat,
        lng: @currentLong,
        zoom: 13,
        height: '400px'
      }
      @addMarker4CurrentPosition()

    drawSearchResults: ->
      if(@gmaps)
        @cleanRoute()
        @gmap.removeMarkers()
        @addMarker4CurrentPosition()

        number = 1
        searchResults.each (searchResult) =>
          offer = searchResult.get('offer')
          lat = offer.flatshare.geoLocation.latitude
          long = offer.flatshare.geoLocation.longitude
          street = offer.flatshare.address.street + ' ' + offer.flatshare.address.streetNumber
          city = offer.flatshare.address.city;
          title = street + ', ' + city
          @addMarker lat,long,title,number, '<p>Straße: '+street+'<br/>
            Ort:'+city+'<br/>Miete: '+offer.roomDetails.totalRentPerMonthInEuro+' €<br/>
            Zimmergröße: '+offer.roomDetails.roomSize.squareMeters+' m²</p>'
          number++

    addMarker4CurrentPosition: ->
      marker = @gmap.createMarker {lat:@currentLat, lng:@currentLong, title:'aktueller Standort'}
      marker.setMap(@gmap.map);
      marker.setIcon('/images/home.png');
      @gmap.markers.push(marker);

    addMarker: (latitude, longitude, markertitle, number, infoContent) ->
      iconUrl = 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld='+number+'|85A4BD|000000'
      @gmap.addMarker {lat:latitude, lng:longitude, title:markertitle,icon:iconUrl, infoWindow:{content:infoContent}}

    drawRoute: (routeId) ->
      #console.log 'drawRoute event: id=' + routeId
      
      searchResult = null
      for entry in @searchResults
        #console.log 'search result id=' + entry.roomOffer.flatshare.id
        if entry.roomOffer.flatshare.id == routeId
          #console.log 'found route to draw!!!'
          searchResult = entry

      if searchResult == null
        return

      lat = searchResult.roomOffer.flatshare.geoLocation.latitude
      long = searchResult.roomOffer.flatshare.geoLocation.longitude      
      @cleanRoute()
   
      address = searchResult.roomOffer.flatshare.address.street+ ' ' + searchResult.roomOffer.flatshare.address.streetNumber + ', ' + searchResult.roomOffer.flatshare.address.city
      $('#routeToTarget').html(address)
      $('#routeTo').show()
        
      new_position = $('#SearchResultMap').offset();
      $('html,body').animate({scrollTop:new_position.top}, 1000)

      @gmap.travelRoute {
        origin: [@currentLat, @currentLong],
        destination: [lat, long],
        travelMode: 'walking',
        step: (e) =>
          
          $('#instructions').delay(1000).fadeIn(200, =>
            $('#instructions').append('<li>'+e.instructions+'</li>');
            @gmap.setCenter e.end_location.lat(),e.end_location.lng()
            @gmap.drawPolyline {
              path: e.path,
              strokeColor: '#131540',
              strokeOpacity: 0.6,
              strokeWeight: 6
            }
          )
      }    

    cleanRoute: ->
      $('#instructions > *').remove()
      @gmap.cleanRoute()
      $('#routeTo').hide()
