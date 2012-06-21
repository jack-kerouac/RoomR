(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/LoginWidget', 'widgets/OfferRoomFormWidget'], function(RoomrSection, LoginWidget, OfferRoomFormWidget) {
    'use strict';

    var OferRoomSection;
    return OferRoomSection = (function(_super) {

      __extends(OferRoomSection, _super);

      function OferRoomSection() {
        var loginWidget, offerRoomFormWidget;
        OferRoomSection.__super__.constructor.call(this, {
          name: 'offerRoom',
          title: 'Anzeige erstellen'
        });
        loginWidget = new LoginWidget();
        this.addWidget(loginWidget);
        offerRoomFormWidget = new OfferRoomFormWidget();
        this.addWidget(offerRoomFormWidget);
      }

      return OferRoomSection;

    })(RoomrSection);
  });

}).call(this);
