var io = require('socket.io').listen(1337);

io.sockets.on('connection', function(socket) {
  socket.on('message', function(data) {
  	console.log("Message received");
    console.log(data);
    socket.broadcast.send(data);
  });
});