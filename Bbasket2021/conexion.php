<?php

	$servername = "db4free.net";
	$username = "desarrollo_movil";
	$password = "d3s4rr0ll0m0v1l";
	$database = "bbasket_android";
	$conexion = null;
	
	try {
		$conexion = new PDO("mysql:host=$servername;dbname=$database", $username, $password);
		// set the PDO error mode to exception
		$conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		//echo "Connected successfully";
	} catch(PDOException $e) {
		//echo $sql . "<br>" . $e->getMessage();
		$conexion = null;
	}
	
?>