(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrSection', 'widgets/PhotoUploadWidget', 'widgets/FlatshareWidget'], function(RoomrSection, PhotoUploadWidget, FlatshareWidget) {
    'use strict';

    var TestSection;
    return TestSection = (function(_super) {

      __extends(TestSection, _super);

      function TestSection() {
        var flatshareWidget, photoUploadWidget;
        TestSection.__super__.constructor.call(this, {
          name: 'test',
          title: 'Testbereich'
        });
        photoUploadWidget = new PhotoUploadWidget();
        this.addWidget(photoUploadWidget);
        flatshareWidget = new FlatshareWidget();
        this.addWidget(flatshareWidget);
      }

      return TestSection;

    })(RoomrSection);
  });

}).call(this);
