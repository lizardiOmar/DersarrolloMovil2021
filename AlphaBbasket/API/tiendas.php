<?php
	include 'conexion.php';
	include 'tienda.php';
	
	switch ($_SERVER['REQUEST_METHOD']) {
		
		case 'POST':
		if (
			isset($_POST['id']) &&
			isset($_POST['nombre']) &&
			isset($_POST['horaApertura']) &&
			isset($_POST['horaCierre']) &&
			isset($_POST['idCliente']) ){
				$tienda = new Tienda();
				$tienda->guardarTienda($_POST['id'], $_POST['nombre'], $_POST['horaApertura'], $_POST['horaCierre'], $_POST['idCliente']);
		} else{
			$response = array(
				"SERVICIO"=>"ERROR",
				"ESTADO"=>"DATA NULA"
				);
		}
		break;
		
	}
?>