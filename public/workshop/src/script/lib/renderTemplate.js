(function() {

  define(['Handlebars'], function() {
    'use strict';

    var templateCache;
    templateCache = {};
    return function(templateName, context, callback) {
      if (templateCache[templateName] != null) {
        callback(templateCache[templateName](context));
        return;
      }
      return $.get("/templates/" + templateName + ".handlebars", function(tpl) {
        var template;
        template = Handlebars.compile(tpl);
        templateCache[templateName] = template;
        return callback(template(context));
      });
    };
  });

}).call(this);
