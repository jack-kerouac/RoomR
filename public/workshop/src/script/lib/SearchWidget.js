(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['backbone', 'lib/RoomrWidget', 'lib/roomrUtil', 'lib/SearchResult'], function(Backbone, RoomrWidget, roomrUtil, SearchResult) {
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
      }

      SearchWidget.prototype.performSearch = function() {
        var _this = this;
        this.searchResults.url = 'rest/roomOffers/search?' + $('#search_offers_form').serialize();
        return this.searchResults.fetch({
          success: function(results) {
            var searchResultsArray;
            searchResultsArray = [];
            results.each(function(result) {
              return searchResultsArray.push(new SearchResult(result.offer, result.matchingScore));
            });
            return _this.emit('searchResultsChanged', searchResultsArray);
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

      return SearchWidget;

    })(RoomrWidget);
  });

}).call(this);
