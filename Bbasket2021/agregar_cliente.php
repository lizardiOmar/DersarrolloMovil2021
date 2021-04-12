<?php
	include 'conexion.php';
	
	$id = $_POST['id'];
	$nombres = $_POST['nombres'];
	$apellidos = $_POST['apellidos'];
	$correo = $_POST['correo'];
	$edad = $_POST['edad'];
	$contrasena = $_POST['contrasena'];
	
	try {
		$response = array();
		$sql = "INSERT INTO cliente (id, nombres, apellidos, correo, edad, contrasena)
		VALUES ($id, '$nombres', '$apellidos', '$correo', $edad, '$contrasena')";
		$conexion->exec($sql);
		$response['success'] = 'true';
		$response['message'] = 'Cliente creado';
	} catch(PDOException $e) {
		$response['success'] = 'false';
		$response['message'] = 'Cliente no creado';
		$conexion = null;
	}
	
	return json_encode($response);
?>