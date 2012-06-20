(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['lib/RoomrWidget', 'lib/renderTemplate'], function(RoomrWidget, renderTemplate) {
    'use strict';

    var SearchResultWidget;
    return SearchResultWidget = (function(_super) {

      __extends(SearchResultWidget, _super);

      SearchResultWidget.prototype.nidus = void 0;

      function SearchResultWidget() {
        SearchResultWidget.__super__.constructor.call(this, 'searchResult');
      }

      SearchResultWidget.prototype.renderInto = function(element) {
        var _this = this;
        this.nidus = $(element);
        return this.renderTemplate({}, function(content) {
          return _this.nidus.html(content);
        });
      };

      SearchResultWidget.prototype.searchResultsChanged = function(searchResults) {
        var _this = this;
        return _.each(searchResults, function(result) {
          return _this.nidus.append(result.toString());
        });
      };

      return SearchResultWidget;

    })(RoomrWidget);
  });

}).call(this);
