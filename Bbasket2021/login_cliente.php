<?php
	include 'conexion.php';
	$response = null;
	if (isset($_POST['correo'])) {
		
		
		$correo = $_POST['correo'];
		$sql = "SELECT * FROM cliente where correo='$correo'";
		try {
			$stmt = $conexion->query($sql);
			$result = $stmt->setFetchMode(PDO::FETCH_NUM);
			while ($row = $stmt->fetch()) {
				$id = $row[0];
				$nombres = $row[1];
				$apellidos = $row[2];
				$correo = $row[3];
				$edad = $row[4];
				$contrasena = $row[5];
				
			}
			$response = array(
				"estado"=>"LOGEADO",
				"cliente"=>array(
					"id"=>$id,
					"nombres"=>$nombres,
					"apellidos"=>$apellidos,
					"correo"=>$correo,
					"edad"=>$edad,
					"contrasena"=>$contrasena
				)
			);
			
			//$sql_lista = "INSERT INTO access_list (id, CORREO, FECHA, RESULTADO)
			//VALUES (0, '$correo', CURRENT_TIMESTAMP, 'TRUE')";
			//$conexion->exec($sql_lista);
			echo json_encode($response);

		} catch (PDOException $e) {
			$response = array(
				"estado"=>"FALLIDO",
				"cliente"=>"NULL"
			);
			//$sql_lista = "INSERT INTO access_list (id, CORREO, FECHA, RESULTADO)
			//VALUES (0, '$correo', CURRENT_TIMESTAMP, 'FALSE')";
			//$conexion->exec($sql_lista);
			echo json_encode($response);
		}
	}else{
		$response = array(
				"estado"=>"CORREO_NULO",
				"cliente"=>"NULL"
			);
			echo json_encode($response);
	}
	
?>