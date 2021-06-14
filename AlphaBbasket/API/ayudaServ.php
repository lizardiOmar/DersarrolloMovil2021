<?php
	include 'conexion.php';
	include 'ayuda.php';
	
	switch ($_SERVER['REQUEST_METHOD']) {
		
		case 'GET':
			Ayuda::obtenerInfo();
		break;
		
		case 'POST':
		if (
			isset($_POST['id']) &&
			isset($_POST['util']) &&
			isset($_POST['idC']) ){
				$ayuda = new Ayuda($_POST['id'], $_POST['util'], $_POST['idC']);
				$ayuda->guardarRespuesta();
		} else{
			$response = array(
				"SERVICIO"=>"ERROR",
				"ESTADO"=>"DATA NULA"
				);
		}
		break;
		
	}
?>