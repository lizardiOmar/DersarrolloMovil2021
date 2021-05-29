<?php
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
				$cliente->guardarCliente();
			}
			if (isset($_POST['id'])&&isset($_POST['dato'])&&isset($_POST['index_columna'])) {
				$id=$_POST['id'];
				$dato=$_POST['dato'];
				$index=$_POST['index_columna'];
				Cliente::actualizarCliente($id, $dato, $index);
			}else{
				$response=array(
					"NADA"=>"PASO"
				);
				return json_encode($response);
			}
		break;
		//Leer clientes o cliente GET
		case 'GET':
			if (isset($_GET['correo'])) {
				Cliente::mostrarCliente( $_GET['correo']);
			}
		break;
		//Actualizar cliente UPDATE
		case 'PUT':
			
		break;
		//Eliminar cliente DELETE
		case 'DELETE':
			if(isset($_GET['correo'])){
			$correo=$_GET['correo'];
				Cliente::borrarCliente($correo);
			}		
		break;
	}
?>