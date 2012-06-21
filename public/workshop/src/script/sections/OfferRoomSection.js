(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/OfferRoomFormWidget'], function(RoomrSection, OfferRoomFormWidget) {
    'use strict';

    var OferRoomSection;
    return OferRoomSection = (function(_super) {

      __extends(OferRoomSection, _super);

      function OferRoomSection() {
        var offerRoomFormWidget;
        OferRoomSection.__super__.constructor.call(this, {
          name: 'offerRoom',
          title: 'Anzeige erstellen'
        });
        offerRoomFormWidget = new OfferRoomFormWidget();
        this.addWidget(offerRoomFormWidget);
      }

      return OferRoomSection;

    })(RoomrSection);
  });

}).call(this);
