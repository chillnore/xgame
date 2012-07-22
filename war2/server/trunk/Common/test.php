<?php

// 设置一些基本的变量
$host = "127.0.0.1";
$port = 4400;
// 设置超时时间
set_time_limit(0);

// 创建一个Socket
$tcp = getprotobyname("tcp");  
$socket = socket_create(AF_INET, SOCK_STREAM, $tcp);
socket_connect($socket, $host, $port);

// 消息体
$msg = json_encode(array(
	"msgTypeID" => 1001, 
	"msgBody" => array(
		"ticket" => "123456", 
	),
));

echo "send $msg";

// 写出消息
socket_write($socket, $msg, strlen($msg));
// 关闭 socket
socket_close($socket);
