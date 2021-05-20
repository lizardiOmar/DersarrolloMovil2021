<?php
	$servername = "localhost";
	$username = "root";
	$password = "";
	$database = "bbasket";
	$conexion = null;
	$estado_servidor="desconectado";
	try {
		$conexion = new PDO("mysql:host=$servername;dbname=$database", $username, $password);
		$conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$estado_servidor= "conectado";
	} catch(PDOException $e) {
		$conexion = null;
		$estado_servidor= "desconectado";
	}
?>