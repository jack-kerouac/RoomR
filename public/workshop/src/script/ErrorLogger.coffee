define -> 
  postError = (errorMsg) ->    
    currentDate = new Date().toLocaleString()
    errorMsg = "[#{currentDate}] #{errorMsg}" 
    $.ajax({
      url:'http://localhost:9000/rest/log',
      type:'POST',
      data:"{ 'message' : '#{errorMsg}', 'severity' : 'ERROR'}",
      contentType:"application/json; charset=utf-8"
    })

  window.onerror = (message,url,line_num) -> 
    errorMsg = "General error in #{url} at line #{line_num}: #{message}"
    postError errorMsg

  $(window).bind 'widgetError', (event, widgetName, msg)->    
    errorMsg = "Widget error in #{widgetName}: #{msg}"
    postError errorMsg
