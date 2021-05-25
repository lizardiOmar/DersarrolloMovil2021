<?php
	//http://localhost/API/clientes.php
	include 'conexion.php';
	include 'cliente.php';
	//echo "El metodo utilizado es: " . $_SERVER['REQUEST_METHOD'];
	switch ($_SERVER['REQUEST_METHOD']) {
		//Crear cliente POST
		case 'POST':
			if (
			isset($_POST['id']) &&
			isset($_POST['nombres']) &&
			isset($_POST['apellidos']) &&
			isset($_POST['correo']) &&
			isset($_POST['edad']) &&
			isset($_POST['clave'])) {
				
				$cliente =  new Cliente($_POST['id'], $_POST['nombres'], $_POST['apellidos'], $_POST['correo'], $_POST['edad'], $_POST['clave']);
				$response = null;
				try {	
					$sql = "INSERT INTO cliente (id, nombres, apellidos, correo, edad, clave, fecha_alta)
					VALUES ($cliente->id, '$cliente->nombres', '$cliente->apellidos', '$cliente->correo', $cliente->edad, '$cliente->clave', CURRENT_TIMESTAMP())";
					$conexion->exec($sql);
					$response = array(
						"SERVICIO"=>$estado_servidor,
						"ESTADO"=>"CREADO"
					);
				} catch(PDOException $e) {
					$response = array(
						"SERVICIO"=>$estado_servidor,
						"ESTADO"=>"NO CREADO",
						"ERROR"=>$e
					);
				}
					
				
				
			}else{
				$response = array(
						"SERVICIO"=>"ERROR",
						"ESTADO"=>"DATA NULA"
				);
			}
			$conexion = null;
			echo json_encode($response);
		break;
		//Leer clientes o cliente GET
		case 'GET':
		$response = null;
		if (isset($_GET['correo'])) {
			$correo = $_GET['correo'];
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
					$clave = $row[5];
				}
			$cliente =  new Cliente("$id", "$nombres", "$apellidos", "$correo", "$edad", "$clave");
			
			$response = array(
				"cliente"=>$cliente
			);
			echo json_encode($response);
		} catch (PDOException $e) {
			$response = array(
				"estado"=>"FALLIDO",
				"cliente"=>"NULL"
			);
			echo json_encode($response);
		}
	}else{
		
		$cliente =  new Cliente("90", "Omar", "Alonso", "correo_electronico", "edad", "clave");
		$cliente->guardarCliente();
		
	}	
		break;
		//Actualizar cliente UPDATE
		case 'PUT':
		if(isset($_POST['nombres']) &&
			isset($_POST['apellidos']) &&
			isset($_POST['edad'])&&
			isset($_POST['correo'])){
				$nombres = $_POST['nombres'];
				$apellidos = $_POST['apellidos'];
				$correo = $_POST['correo'];
				$edad = $_POST['edad'];
				$sql = "UPDATE cliente SET nombres='$nombres', apellidos='$apellidos', edad=$edad WHERE correo='$correo';";
				if ($conexion->query($sql) === TRUE) {
					echo "Record updated successfully";
				} else {
					echo "Error updating record: internal" ;
				}
			}else{
				echo "Error updating record: external" ;
			}
		
		break;
		//Eliminar cliente DELETE
		case 'DELETE':
		$response = null;
		if(isset($_GET['correo'])){
		$correo=$_GET['correo'];
		
		try {
			$sql = "DELETE FROM cliente WHERE correo='$correo'";
			$conexion->exec($sql);
			
			if ($conexion->query($sql) == TRUE) {
				$response = array(
					"estado"=>"TRUE",
					"MENSAJE"=>"Cuenta eliminada"
				);
			} else{
				$response = array(
					"estado"=>"FALSE",
					"MENSAJE"=>"Cuenta No eliminada"
				);
			}
		} catch (PDOException $e) {
			$response = array(
				"estado"=>"FALSE",
				"MENSAJE"=>$e
			);
		}
	}else{
		$response = array(
			"estado"=>"FALSE",
			"MENSAJE"=>"CLIENTE NULO"
		);
	}
	
	echo json_encode($response);
		break;
	}
?>