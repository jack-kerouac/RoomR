(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/CreateUserWidget'], function(RoomrSection, CreateUserWidget) {
    'use strict';

    var MainSection;
    return MainSection = (function(_super) {

      __extends(MainSection, _super);

      function MainSection() {
        var createUserWidget;
        MainSection.__super__.constructor.call(this, {
          name: 'main',
          title: 'Hauptseite',
          path: ''
        });
        createUserWidget = new CreateUserWidget();
        this.addWidget(createUserWidget);
      }

      return MainSection;

    })(RoomrSection);
  });

}).call(this);
