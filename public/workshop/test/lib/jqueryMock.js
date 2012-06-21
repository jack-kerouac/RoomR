(function() {

  define(function() {
    var options;
    options = null;
    return {
      mockAjax: function() {
        return $.ajax = function(param) {
          return options = param;
        };
      },
      respond: function(content) {
        return options.success(content);
      },
      url: function() {
        return options.url;
      }
    };
  });

}).call(this);
