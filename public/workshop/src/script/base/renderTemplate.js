(function() {

  define(['Handlebars', 'underscore'], function() {
    'use strict';

    var defaultErrorHandler, templateCache;
    Handlebars.registerHelper('date', function(date) {
      return new Date(date).toLocaleDateString();
    });
    templateCache = {};
    defaultErrorHandler = function(templateName, options) {
      return $(window).trigger('templateLoadError', "received " + options.status + " when requesting template " + templateName);
    };
    return function(templateName, context, callback, errorCallback) {
      if (errorCallback == null) {
        errorCallback = defaultErrorHandler;
      }
      if (templateCache[templateName] != null) {
        callback(templateCache[templateName](context));
        return;
      }
      return $.ajax({
        url: "/templates/" + templateName + ".handlebars",
        type: 'GET',
        success: function(tpl) {
          var template;
          template = Handlebars.compile(tpl);
          templateCache[templateName] = template;
          return callback(template(context));
        },
        error: _.bind(errorCallback, this, templateName)
      });
    };
  });

}).call(this);
