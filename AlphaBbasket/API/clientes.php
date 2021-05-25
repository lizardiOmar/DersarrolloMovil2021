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
				$cliente->guardarCliente();
			}
		break;
		//Leer clientes o cliente GET
		case 'GET':
			if (isset($_GET['correo'])) {
				$correo = $_GET['correo'];
				Cliente::mostrarCliente('$correo');
			}
		break;
		//Actualizar cliente UPDATE
		case 'PUT':
			if (isset($_POST['id'])&&isset($_POST['dato'])&&isset($_POST['index_columna'])) {
				Cliente::actualizarCliente($_POST['index_columna'], $_POST['dato'], $_POST['id']);
			}
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