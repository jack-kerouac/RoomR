$(window).error (event, params...) -> 
  console.log "Event: " + event + " params: " + params unless console.log?
  $.post 'error.html', params