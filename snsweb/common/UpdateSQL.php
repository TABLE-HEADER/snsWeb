<?php
	$sql = $_POST['sql'];
	$link = mysql_connect('localhost', 'root', 'root123');
	mysql_select_db('snsdb');
	mysql_query($sql);
	mysql_close();
	echo 'test';
	die();
?>