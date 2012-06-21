define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class MapWidget extends RoomrWidget

    nidus: undefined

    gmap: undefined  

    currentLat: undefined
    currentLong: undefined

    constructor: ->
      super('map')
      @subscribeToEvent 'searchResultsChanged', (params) =>
        @searchResultsChanged params

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {}, (html) =>
        @nidus.html html
        @loadGoogle()
              
    loadGoogle: ->
      window.roomr = window.roomr || {}
      window.roomr.roomrMapWidgetDrawCallback = @loadGmaps.bind(this)
      $.getScript 'http://maps.google.com/maps/api/js?sensor=false&callback=roomr.roomrMapWidgetDrawCallback'

    loadGmaps: ->
      $.getScript('src/script/vendor/gmaps.js', @renderMap.bind(this)).fail (args...) -> console.log args

    renderMap: ->      
      $(document).ready =>
        if navigator.geolocation
          navigator.geolocation.getCurrentPosition (position) =>
            @createMap position.coords.latitude,position.coords.longitude
        else
          @createMap '-12.043333','-77.028333'          

    createMap: (latitude, longitude) ->
      @currentLat = latitude
      @currentLong = longitude
      @gmap = new GMaps {
        div: '#SearchResultMap',
        lat: latitude,
        lng: longitude,
        zoom: 13,
        height: '400px'
      }
      @addMarker4CurrentPosition()      

    addMarker4CurrentPosition: ->
      marker = @gmap.createMarker {lat:@currentLat, lng:@currentLong, title:'aktueller Standort'}
      marker.setMap(@gmap.map);
      marker.setIcon('src/style/img/home.png');
      @gmap.markers.push(marker);

    addMarker: (latitude, longitude, markertitle, number, infoContent) ->
      iconUrl = 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld='+number+'|85A4BD|000000'
      @gmap.addMarker {lat:latitude, lng:longitude, title:markertitle,icon:iconUrl, infoWindow:{content:infoContent}}

    searchResultsChanged: (searchResults) ->
      @gmap.cleanRoute()
      @gmap.removeMarkers()
      @addMarker4CurrentPosition()
      number = 1
      for searchResult in searchResults
        
        lat = searchResult.roomOffer.flatshare.geoLocation.latitude
        long = searchResult.roomOffer.flatshare.geoLocation.longitude
        street = searchResult.roomOffer.flatshare.address.street + ' ' + searchResult.roomOffer.flatshare.address.streetNumber
        city = searchResult.roomOffer.flatshare.address.city;
        title = street + ', ' + city
        @addMarker lat,long,title,number, '<p>Straße: '+street+'<br/>
          Ort:'+city+'<br/>Miete: '+searchResult.roomOffer.roomDetails.totalRentPerMonthInEuro+' €<br/>
          Zimmergröße: '+searchResult.roomOffer.roomDetails.roomSize.squareMeters+' m²</p>'
        number++

      $('.searchResult').each (e, domElem) =>
        $(domElem).dblclick =>                    
          lat = $(domElem).data 'result-latitude'
          long = $(domElem).data 'result-longitude'
          @gmap.cleanRoute()
          @gmap.drawRoute({
            origin: [@currentLat, @currentLong],
            destination: [lat, long],
            travelMode: 'walking',
            strokeColor: '#131540',
            strokeOpacity: 0.6,
            strokeWeight: 6
          });
      