(function() {

  require(['base/EventMediator', 'backbone', 'Navigation', 'models/UserCollection', 'base/AppRouter', 'base/RoomrSection', 'sections/OfferRoomSection', 'widgets/PhotoUploadWidget', 'sections/SearchSection', 'sections/MainSection'], function(EventMediator, Backbone, Navigation, UserCollection, AppRouter, RoomrSection, OfferRoomSection, PhotoUploadWidget, SearchSection, MainSection) {
    'use strict';

    var app, mainSection, offerRoomSection, photoUploadWidget, searchSection, testSection;
    app = new AppRouter();
    mainSection = new MainSection();
    app.addSection(mainSection);
    searchSection = new SearchSection();
    app.addSection(searchSection);
    offerRoomSection = new OfferRoomSection();
    app.addSection(offerRoomSection);
    testSection = new RoomrSection({
      name: 'test',
      title: 'test 2 title'
    });
    photoUploadWidget = new PhotoUploadWidget();
    testSection.addWidget(photoUploadWidget);
    app.addSection(testSection);
    return $(document).ready(function() {
      return Backbone.history.start({
        pushState: true
      });
    });
  });

}).call(this);
