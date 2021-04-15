<?php
	include 'conexion.php';
    $response = null;
	if (isset($_POST['correo'])) {
		$correo = $_POST['correo'];
		$sql = "SELECT * FROM cliente where correo='$correo';";
		try {
			$stmt = $conexion->query($sql);
			$result = $stmt->setFetchMode(PDO::FETCH_NUM);
			while ($row = $stmt->fetch()) {
				$contrasena = $row[5];
			}
			$response = array(
				"estado"=>"exito",
				"datos"=>array(
					"correo"=>$correo,
					"contrasena"=>$contrasena
				)
			);
			
			
			
			echo json_encode($response);
		} catch (PDOException $e) {
			$response = array(
            "estado"=>"error",
            "datos"=>""
			);
			echo json_encode($response);
		}
	}else{
		$response = array(
            "estado"=>"error",
            "datos"=>""
		);
		echo json_encode($response);
	}
	
?>