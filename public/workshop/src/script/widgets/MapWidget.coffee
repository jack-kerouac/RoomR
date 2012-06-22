define ['base/RoomrWidget', 'base/renderTemplate'], (RoomrWidget, renderTemplate) ->
  'use strict'

  class MapWidget extends RoomrWidget

    nidus: undefined
    
    gmap: undefined  

    currentLat: undefined
    currentLong: undefined

    constructor: ->
      super('map')
      @searchResults = []
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
      if window.roomr.isGoogleMapJSLoaded
        @createMap @currentLat,@currentLong
        @drawSearchResults()
      else
        $.getScript 'http://maps.google.com/maps/api/js?sensor=false&callback=roomr.roomrMapWidgetDrawCallback'

    loadGmaps: ->
      window.roomr.isGoogleMapJSLoaded = yes
      $.getScript('src/script/vendor/gmaps.js', @renderMap.bind(this)).fail (args...) -> console.log args

    renderMap: ->    
      console.log 'renderMap'
      $(document).ready =>
        #if navigator.geolocation
        #  navigator.geolocation.getCurrentPosition (position) =>
        #    @createMap position.coords.latitude,position.coords.longitude
        #else
        console.log 'start creating map'
        @createMap '48.139126','11.580186'          
        @drawSearchResults()

    createMap: (latitude, longitude) ->
      console.log 'create map'
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
      console.log 'create map... finished'

    addMarker4CurrentPosition: ->
      marker = @gmap.createMarker {lat:@currentLat, lng:@currentLong, title:'aktueller Standort'}
      marker.setMap(@gmap.map);
      marker.setIcon('src/style/img/home.png');
      @gmap.markers.push(marker);

    addMarker: (latitude, longitude, markertitle, number, infoContent) ->
      iconUrl = 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld='+number+'|85A4BD|000000'
      @gmap.addMarker {lat:latitude, lng:longitude, title:markertitle,icon:iconUrl, infoWindow:{content:infoContent}}

    searchResultsChanged: (searchResults) ->
      @searchResults = searchResults
      @cleanRoute()
      @gmap.removeMarkers()
      @addMarker4CurrentPosition()
      @drawSearchResults()  

    cleanRoute: ->
      $('#instructions > *').remove()
      @gmap.cleanRoute()

    drawSearchResults: ->  
      number = 1
      #console.log 'drawSearchResults' + @searchResults.length
      if @searchResults.length < 1
        return
      for searchResult in @searchResults
        
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
          @cleanRoute()
          #@gmap.drawRoute({
          #  origin: [@currentLat, @currentLong],
          #  destination: [lat, long],
          #  travelMode: 'walking',
          #  strokeColor: '#131540',
          #  strokeOpacity: 0.6,
          #  strokeWeight: 6
          #});
          @gmap.travelRoute {
            origin: [@currentLat, @currentLong],
            destination: [lat, long],
            travelMode: 'walking',
            step: (e) =>
              $('#instructions').append('<li>'+e.instructions+'</li>');
              $('#instructions li:eq(' + e.step_number + ')').delay(450 * e.step_number).fadeIn(200, =>
                @gmap.setCenter e.end_location.lat(),e.end_location.lng()
                @gmap.drawPolyline {
                  path: e.path,
                  strokeColor: '#131540',
                  strokeOpacity: 0.6,
                  strokeWeight: 6
                }
              )
          }    