<?php
	//http://localhost/API/clientes.php
	//sinclude 'conexion.php';
	include 'direccion.php';
	//echo "El metodo utilizado es: " . $_SERVER['REQUEST_METHOD'];
	switch ($_SERVER['REQUEST_METHOD']) {
		//Crear direccion POST
		case 'POST':
			if (
			isset($_POST['id']) &&
			isset($_POST['latitud']) &&
			isset($_POST['longitud']) &&
			isset($_POST['altura']) &&
			isset($_POST['correo']) ){
				$direccion = new Direccion($_POST['id'], $_POST['latitud'], $_POST['longitud'], $_POST['altura'], $_POST['correo']);
				$direccion->guardarDireccion();
				/*$direccion =  new Direccion($_POST['id'], $_POST['latitud'], $_POST['longitud'], $_POST['altura'], $_POST['correo']);
				$response = null;
				try {	
					$sql = "INSERT INTO direccion (id, latitud, longitud, altura, correo)
					VALUES ($direccion->id, $direccion->latitud, $direccion->longitud, $direccion->altura, '$direccion->correo')";
					
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
				}*/
			}else{
				$response = array(
						"SERVICIO"=>"ERROR",
						"ESTADO"=>"DATA NULA"
				);
				/*$direccion = new Direccion("5", "3410", "54132", "410", "asi@correo.com");
				$direccion->guardarDireccion();*/
			}
			echo json_encode($response);
		break;
		//Leer clientes o cliente GET
		case 'GET':
			Direccion::mostrarDireccion($_GET['id']);
		break;
		//Actualizar cliente UPDATE
		case 'PUT':
			Direccion::actualizarDireccion($_GET['id'],$_GET['latitud'],$_GET['longitud'],$_GET['altura']);
		break;
		//Eliminar cliente DELETE
		case 'DELETE':
			Direccion::borrarDireccion($_GET['correo']);
		/*$response = null;
		if(isset($_GET['correo'])){
		$correo=$_GET['correo'];
		
		try {
			$sql = "DELETE FROM direccion WHERE correo='$correo'";
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
	
	echo json_encode($response);*/
		break;
	}
?>