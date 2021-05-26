<?php
	require_once 'conexion.php';
	class Login{
		
		
		var $id;
		var $estatus;
		var $idCliente;
		
		public function __construct($id, $estatus, $idCliente){
			$this->id 			= $id;
			$this->estatus 		= $estatus;
			$this->idCliente 	= $idCliente;
		}
		
		public function guardarLogin(){	
			$response=null;
			try {	
				$sql = "INSERT INTO login_list (id, fecha, estatus, idCliente)
				VALUES ('$this->id', CURRENT_TIMESTAMP(), '$this->estatus', '$this->idCliente')";
				$conn=new Conexion();
				$conn->getConexion()->exec($sql);
				$response = array(
					"Login"=>"Registrado",
					"Estatus"=>$this->estatus
				);
			} catch(PDOException $e) {
				$response = array(
					"SERVICIO"=>"Login",
					"ESTADO"=>"NO CREADO",
					"ERROR"=>$e
				);
			}
			$conn = null;
			return json_encode($response);
		}
	}
	
?>	