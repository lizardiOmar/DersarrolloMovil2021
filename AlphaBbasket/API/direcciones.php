<?php
	//http://localhost/API/clientes.php
	include 'conexion.php';
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
				$direccion =  new Direccion($_POST['id'], $_POST['latitud'], $_POST['longitud'], $_POST['altura'], $_POST['correo']);
				$direccion->guardarDireccion();
			}
			if (isset($_POST['correo'])&&
			isset($_POST['latitud']) &&
			isset($_POST['longitud']) &&
			isset($_POST['altura'])) {
				$correo=$_POST['correo'];
				$latitud=$_POST['latitud'];
				$longitud=$_POST['longitud'];
				$altura=$_POST['altura'];
				Direccion::actualizarDireccion($correo, $latitud, $longitud, $altura);
			}
			
		break;
		//Leer clientes o cliente GET
		case 'GET':
			if (isset($_GET['correo']) ){
				Direccion:: mostrarDireccion($_GET['correo']);
			}else{
				$correo='heandsheofi@gmail.com';
				$latitud=100.00;
				$longitud=100.00;
				$altura=100.00;
				Direccion::actualizarDireccion($correo, $latitud, $longitud, $altura);
			}
		break;
		//Actualizar cliente UPDATE
		case 'PUT':
		
		//Eliminar cliente DELETE
		case 'DELETE':
			if(isset($_GET['correo'])){
				$correo=$_GET['correo'];
				Direccion::borrarDireccion($correo);
			}
			
		break;
	}
?>