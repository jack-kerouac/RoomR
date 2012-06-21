(function() {

  require(['base/EventMediator', 'backbone', 'base/AppRouter', 'base/RoomrSection', 'sections/OfferRoomSection', 'widgets/PhotoUploadWidget', 'sections/SearchSection', 'sections/MainSection', 'widgets/LoginWidget'], function(EventMediator, Backbone, AppRouter, RoomrSection, OfferRoomSection, PhotoUploadWidget, SearchSection, MainSection, LoginWidget) {
    'use strict';

    var app, loginWidget, mainSection, offerRoomSection, photoUploadSection, photoUploadWidget, searchSection;
    app = new AppRouter();
    loginWidget = new LoginWidget();
    loginWidget.renderInto($('#login'));
    mainSection = new MainSection();
    app.addSection(mainSection);
    searchSection = new SearchSection();
    app.addSection(searchSection);
    offerRoomSection = new OfferRoomSection();
    app.addSection(offerRoomSection);
    photoUploadSection = new RoomrSection({
      name: 'photoUpload',
      title: 'Foto Upload'
    });
    photoUploadWidget = new PhotoUploadWidget();
    photoUploadSection.addWidget(photoUploadWidget);
    app.addSection(photoUploadSection);
    return $(document).ready(function() {
      return Backbone.history.start({
        pushState: true
      });
    });
  });

}).call(this);
