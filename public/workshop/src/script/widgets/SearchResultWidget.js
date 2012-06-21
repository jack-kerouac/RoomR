(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrWidget'], function(RoomrWidget) {
    'use strict';

    var SearchResultWidget;
    return SearchResultWidget = (function(_super) {

      __extends(SearchResultWidget, _super);

      SearchResultWidget.prototype.nidus = void 0;

      SearchResultWidget.prototype.searchResults = [];

      function SearchResultWidget() {
        var _this = this;
        SearchResultWidget.__super__.constructor.call(this, 'searchResult');
        this.subscribeToEvent('searchResultsChanged', function(params) {
          return _this.searchResultsChanged(params);
        });
      }

      SearchResultWidget.prototype.renderInto = function(element) {
        var _this = this;
        this.nidus = $(element);
        return this.renderTemplate({
          searchResults: this.searchResults
        }, function(content) {
          return _this.nidus.html(content);
        });
      };

      SearchResultWidget.prototype.searchResultsChanged = function(searchResults) {
        this.searchResults = searchResults;
        return this.renderInto(this.nidus);
      };

      return SearchResultWidget;

    })(RoomrWidget);
  });

}).call(this);
