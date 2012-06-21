(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    __slice = [].slice;

  define(['base/RoomrWidget', 'base/renderTemplate'], function(RoomrWidget, renderTemplate) {
    'use strict';

    var MapWidget;
    return MapWidget = (function(_super) {

      __extends(MapWidget, _super);

      MapWidget.prototype.nidus = void 0;

      MapWidget.prototype.gmap = void 0;

      MapWidget.prototype.currentLat = void 0;

      MapWidget.prototype.currentLong = void 0;

      function MapWidget() {
        var _this = this;
        MapWidget.__super__.constructor.call(this, 'map');
        this.subscribeToEvent('searchResultsChanged', function(params) {
          return _this.searchResultsChanged(params);
        });
      }

      MapWidget.prototype.renderInto = function(element) {
        var _this = this;
        this.nidus = $(element);
        return this.renderTemplate({}, function(html) {
          _this.nidus.html(html);
          return _this.loadGoogle();
        });
      };

      MapWidget.prototype.loadGoogle = function() {
        window.roomr = window.roomr || {};
        window.roomr.roomrMapWidgetDrawCallback = this.loadGmaps.bind(this);
        return $.getScript('http://maps.google.com/maps/api/js?sensor=false&callback=roomr.roomrMapWidgetDrawCallback');
      };

      MapWidget.prototype.loadGmaps = function() {
        return $.getScript('src/script/vendor/gmaps.js', this.renderMap.bind(this)).fail(function() {
          var args;
          args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
          return console.log(args);
        });
      };

      MapWidget.prototype.renderMap = function() {
        var _this = this;
        return $(document).ready(function() {
          if (navigator.geolocation) {
            return navigator.geolocation.getCurrentPosition(function(position) {
              return _this.createMap(position.coords.latitude, position.coords.longitude);
            });
          } else {
            return _this.createMap('-12.043333', '-77.028333');
          }
        });
      };

      MapWidget.prototype.createMap = function(latitude, longitude) {
        this.currentLat = latitude;
        this.currentLong = longitude;
        this.gmap = new GMaps({
          div: '#SearchResultMap',
          lat: latitude,
          lng: longitude,
          zoom: 13,
          height: '400px'
        });
        return this.addMarker4CurrentPosition();
      };

      MapWidget.prototype.addMarker4CurrentPosition = function() {
        var marker;
        marker = this.gmap.createMarker({
          lat: this.currentLat,
          lng: this.currentLong,
          title: 'aktueller Standort'
        });
        marker.setMap(this.gmap.map);
        marker.setIcon('src/style/img/home.png');
        return this.gmap.markers.push(marker);
      };

      MapWidget.prototype.addMarker = function(latitude, longitude, markertitle) {
        return this.gmap.addMarker({
          lat: latitude,
          lng: longitude,
          title: markertitle
        });
      };

      MapWidget.prototype.searchResultsChanged = function(searchResults) {
        var city, lat, long, searchResult, street, title, _i, _len, _results;
        this.gmap.removeMarkers();
        this.addMarker4CurrentPosition();
        _results = [];
        for (_i = 0, _len = searchResults.length; _i < _len; _i++) {
          searchResult = searchResults[_i];
          lat = searchResult.roomOffer.flatshare.geoLocation.latitude;
          long = searchResult.roomOffer.flatshare.geoLocation.longitude;
          street = searchResult.roomOffer.flatshare.address.street + ' ' + searchResult.roomOffer.flatshare.address.streetNumber;
          city = searchResult.roomOffer.flatshare.address.city;
          title = street + ', ' + city;
          _results.push(this.addMarker(lat, long, title));
        }
        return _results;
      };

      return MapWidget;

    })(RoomrWidget);
  });

}).call(this);
