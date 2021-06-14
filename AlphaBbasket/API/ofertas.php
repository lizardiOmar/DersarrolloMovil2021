<?php
	include 'conexion.php';
	include 'oferta.php';
	
	switch ($_SERVER['REQUEST_METHOD']) {
		
		case 'GET':
		if (isset($_GET['categoria']) == null && isset($_GET['marca']) == null && isset($_GET['producto']) == null){
			Oferta::allOfertas();
		} else if (isset($_GET['categoria'])){
			Oferta::ofertasCategoria($_GET['categoria']);
		} else if(isset($_GET['marca'])){
			Oferta::ofertasMarca($_GET['marca']);
		} else if(isset($_GET['producto'])){
			Oferta::ofertasProducto($_GET['producto']);
		}
		break;
	}
?>