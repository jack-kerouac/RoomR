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

  $(window).error (event) -> 
    postError event.type

  $(window).bind 'widgetError', (event, widgetName, msg)->    
    errorMsg = "Widget error in #{widgetName}: #{msg}"
    postError errorMsg
    
    