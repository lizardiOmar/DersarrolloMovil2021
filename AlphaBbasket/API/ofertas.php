<?php
	include 'conexion.php';
	include 'oferta.php';
	
	switch ($_SERVER['REQUEST_METHOD']) {
		
		case 'GET':
		if (isset($_GET['categoria']) == null && isset($_GET['marca']) == null){
			Oferta::allOfertas();
		} else if (isset($_GET['categoria'])){
			Oferta::ofertasCategoria($_GET['categoria']);
		} else if(isset($_GET['marca'])){
			Oferta::ofertasMarca($_GET['marca']);
		}
		break;
	}
?>