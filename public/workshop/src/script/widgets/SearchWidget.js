(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['backbone', 'base/RoomrWidget', 'base/roomrUtil', 'models/SearchResult'], function(Backbone, RoomrWidget, roomrUtil, SearchResult) {
    'use strict';

    var SearchWidget;
    return SearchWidget = (function(_super) {
      var SearchResultCollection, SearchResultModel;

      __extends(SearchWidget, _super);

      SearchResultModel = Backbone.Model.extend({
        offer: function() {
          return this.get('offer');
        },
        matchingScore: function() {
          return this.get('matchingScore');
        }
      });

      SearchResultCollection = Backbone.Collection.extend({
        model: SearchResultModel
      });

      SearchWidget.prototype.searchResults = new SearchResultCollection();

      function SearchWidget() {
        SearchWidget.__super__.constructor.call(this, 'search');
        this.registerEvent('searchResultsChanged');
        this.registerGeolocation();
        this.registerShakeEvent();
      }

      SearchWidget.prototype.performSearch = function() {
        var _this = this;
        this.searchResults.url = '/rest/roomOffers/search?' + $('#search_offers_form').serialize();
        return this.searchResults.fetch({
          success: function(results) {
            var completedSearchResults, searchResultsArray, sortedResults;
            searchResultsArray = [];
            completedSearchResults = 0;
            results.each(function(result) {
              var searchResult;
              searchResult = new SearchResult(result.offer(), result.matchingScore(), _this.lat, _this.long);
              return searchResultsArray.push(searchResult);
            });
            sortedResults = _.sortBy(searchResultsArray, function(result) {
              return result.currentDistance;
            });
            return _this.emit('searchResultsChanged', sortedResults);
          }
        });
      };

      SearchWidget.prototype.renderInto = function(element) {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          $(element).append(html);
          roomrUtil.addTypingFinishedCallback($(':input:not(:checkbox, :radio)', $(element)), function() {
            return _this.performSearch();
          });
          return roomrUtil.addTypingFinishedCallback($(':checkbox, :radio', $(element)), function() {
            return _this.performSearch();
          });
        });
      };

      SearchWidget.prototype.registerGeolocation = function() {
        var _this = this;
        return navigator.geolocation.getCurrentPosition(function(position) {
          _this.lat = position.coords.latitude;
          return _this.long = position.coords.longitude;
        });
      };

      SearchWidget.prototype.registerShakeEvent = function() {
        var sensitivity, x1, x2, y1, y2, z1, z2;
        if (window.DeviceMotionEvent != null) {
          sensitivity = 20;
          x1 = y1 = z1 = x2 = y2 = z2 = 0;
          window.addEventListener('devicemotion', function(e) {
            x1 = e.accelerationIncludingGravity.x;
            y1 = e.accelerationIncludingGravity.y;
            return z1 = e.accelerationIncludingGravity.z;
          }, false);
          return setInterval(function() {
            var change;
            change = Math.abs(x1 - x2 + y1 - y2 + z1 - z2);
            if (change > sensitivity) {
              $('#search_offers_form').each(function() {
                return this.reset();
              });
            }
            x2 = x1;
            y2 = y1;
            return z2 = z1;
          }, 150);
        }
      };

      return SearchWidget;

    })(RoomrWidget);
  });

}).call(this);
