define -> 
  $(window).error (event, params...) -> 
    console.log "Event: " + event + " params: " + params unless console.log?
    #if params[0]? and params[1]?
    #  errorMsg = "Widget name: " + params[0] + " message: " + params[1]
    #else
    #  errorMsg = event 
    #$.post 'http://localhost:9000/rest/log', {'message' : 'errorMsg', 'severity' : 'ERROR'}    