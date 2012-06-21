(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/SearchWidget', 'widgets/SearchResultWidget'], function(RoomrSection, SearchWidget, SearchResultWidget) {
    'use strict';

    var SearchSection;
    return SearchSection = (function(_super) {

      __extends(SearchSection, _super);

      function SearchSection() {
        var searchResultWidget, searchWidget;
        SearchSection.__super__.constructor.call(this, {
          name: 'search',
          title: 'Suche'
        });
        searchWidget = new SearchWidget();
        this.addWidget(searchWidget);
        searchResultWidget = new SearchResultWidget();
        this.addWidget(searchResultWidget);
      }

      return SearchSection;

    })(RoomrSection);
  });

}).call(this);
