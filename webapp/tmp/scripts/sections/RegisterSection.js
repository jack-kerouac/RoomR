// Generated by CoffeeScript 1.3.3
(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/CreateUserWidget'], function(RoomrSection, CreateUserWidget) {
    'use strict';

    var RegisterSection;
    return RegisterSection = (function(_super) {

      __extends(RegisterSection, _super);

      function RegisterSection() {
        var createUserWidget;
        RegisterSection.__super__.constructor.call(this, {
          name: 'register',
          title: 'Registrieren'
        });
        createUserWidget = new CreateUserWidget();
        this.addWidget(createUserWidget);
      }

      return RegisterSection;

    })(RoomrSection);
  });

}).call(this);
