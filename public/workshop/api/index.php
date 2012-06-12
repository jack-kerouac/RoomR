<?php


require 'Slim/Slim.php';
$app = new Slim();


function read(){
	$json = file_get_contents('db.json');
	return json_decode($json);
}

function respondJson($obj){
	header('Content-type: application/json');
	echo json_encode($obj);
}

$app->get('/items/', function(){
	$db = read();
	respondJson($db->items);
});

$app->get('/items/:id', function($id){
	$db = read();
	respondJson($db->items[$id]);
});


$app->run();
