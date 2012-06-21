(function() {

  require(['../src/script/base/RoomrSection', 'lib/jqueryMock'], function(RoomrSection, jqueryMock) {
    'use strict';
    return $(document).ready(function() {
      module('Modul RoomrSection');
      return test('create RoomrSection', function() {
        var returnedTemplateContent, testSection;
        testSection = new RoomrSection({
          name: 'test',
          title: 'test title'
        });
        returnedTemplateContent = 'HALLO';
        jqueryMock.mockAjax();
        stop();
        testSection.render(function() {
          var selection;
          selection = $('#test-section');
          equal(selection.length, 1);
          equal(selection.text(), returnedTemplateContent);
          equal(jqueryMock.url(), '/templates/sections/test.handlebars');
          return start();
        });
        return jqueryMock.respond(returnedTemplateContent);
      });
    });
  });

}).call(this);
