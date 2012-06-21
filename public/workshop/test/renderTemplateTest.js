(function() {

  require(['../src/script/base/renderTemplate'], function(renderTemplate) {
    'use strict';
    return $(document).ready(function() {
      module('Modul renderTemplate');
      test('Funktion renderTemplate() ist eine Funktion', function() {
        return equal(typeof renderTemplate, 'function');
      });
      test('Funktion renderTemplate()', function() {
        stop();
        return renderTemplate('../test/resources/test', {}, function(html) {
          ok(html != null);
          equal(html, '<p>TestTemplate</p>');
          return start();
        });
      });
      return test('Funktion renderTemplate() Caching', function() {
        var fetchedHtml;
        stop();
        renderTemplate('../test/resources/test', {}, function(html) {
          return start();
        });
        fetchedHtml = void 0;
        renderTemplate('../test/resources/test', {}, function(html) {
          return fetchedHtml = html;
        });
        ok(fetchedHtml != null);
        return equal(fetchedHtml, '<p>TestTemplate</p>');
      });
    });
  });

}).call(this);
