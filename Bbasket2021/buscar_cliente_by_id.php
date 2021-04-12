<?php
	include 'conexion.php';
	
	$id = $_POST['id'];
	$cliente = array();
	$sql = "SELECT * FROM cliente where id=$id";

	try {
		$stmt = $conexion->query($sql);
		$result = $stmt->setFetchMode(PDO::FETCH_NUM);
		while ($row = $stmt->fetch()) {
			$cliente['id'] = $row[0];
			$cliente['nombres'] = $row[1];
			$cliente['apellidos'] = $row[2];
			$cliente['correo'] = $row[3];
			$cliente['edad'] = $row[4];
			$cliente['contrasena'] = $row[5];
		}
		$response['success'] = 'true';
		$response['message'] = 'cliente Loaded Successfully';
		$response['cliente'] = $cliente;
	} catch (PDOException $e) {
		$response['success'] = 'false';
		$response['message'] = 'cliente Loading Failed';
	}
	echo json_encode($response);
?>