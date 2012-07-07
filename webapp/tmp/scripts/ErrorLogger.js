// Generated by CoffeeScript 1.3.3
(function() {

  define(function() {
    var postError;
    postError = function(errorMsg) {
      var currentDate;
      currentDate = new Date().toLocaleString();
      errorMsg = "[" + currentDate + "] " + errorMsg;
      console.log(errorMsg);
      return $.ajax({
        url: 'http://localhost:9000/rest/log',
        type: 'POST',
        data: "{ 'message' : '" + errorMsg + "', 'severity' : 'ERROR'}",
        contentType: "application/json; charset=utf-8"
      });
    };
    window.onerror = function(message, url, line_num) {
      var errorMsg;
      errorMsg = "General error in " + url + " at line " + line_num + ": " + message;
      return postError(errorMsg);
    };
    $(window).bind('widgetError', function(event, widgetName, msg) {
      var errorMsg;
      errorMsg = "Widget error in " + widgetName + ": " + msg;
      return postError(errorMsg);
    });
    return $(window).bind('templateLoadError', function(event, msg) {
      var errorMsg;
      errorMsg = "Template loading error: " + msg;
      return postError(errorMsg);
    });
  });

}).call(this);
