var recent = [];
var keyCount = 0;

function sendMessage (username,socket){
	var chatMessage =  $('#chatfeld').val();
	if (chatMessage){
		recent.push(chatMessage);
		keyCount = recent.length - 1;
		var data = {message : chatMessage,color : color, username : username};
		socket.send(JSON.stringify(data));
		appendMessage(chatMessage, color, username);
		$('#chatfeld').val('');
	}
};

function appendMessage(messageText, color,username){
	var user = $('<b></b>').text(username + " (" + getTime() + "):");
	var message = $('<div></div>');
	message.text(messageText);
	message.css('color',color);
	message.prepend('&nbsp;')
	message.prepend(user);
	$('#chatarea').append(message);
	$("#chatarea").attr({ scrollTop: $("#chatarea").attr("scrollHeight") });
};

function get_random_color() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.round(Math.random() * 15)];
    }
    return color;
}

function getTime(){
	var date = new Date();
	return format(date.getHours()) + ':' + format(date.getMinutes()) + ':' + format(date.getSeconds());
}

function format(value){
	if (value < 10){
		return '0' + value;
	}
	return value;
}

function fillInHistory(event){
	if (event.keyIdentifier === "Up"){
		if (keyCount < 0){
			keyCount = recent.length - 1;
		}
		$('#chatfeld').val(recent[keyCount]);
		--keyCount;
	} 
}