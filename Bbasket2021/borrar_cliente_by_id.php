<?php
	include 'conexion.php';
	
	$id = $_POST['id'];
	$sql = "DELETE FROM cliente WHERE id=$id";
	
	try {
		$response = array();
		$conexion->exec($sql);
		$response['success'] = 'true';
		$response['message'] = 'Record deleted successfully';
	} catch(PDOException $e) {
		$response['success'] = 'false';
		$response['message'] = 'Data deleted Failed';
		$conexion = null;
	}
	
	echo json_encode($response);
?>