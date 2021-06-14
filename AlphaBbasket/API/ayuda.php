<?php
	class Ayuda{
		var $id;
		var $util;
		var $idCliente;
		
		public function __construct ($id, $util, $idCli){
			$this->id = $id;
			$this->util = $util;
			$this->idCliente = $idCli;
		}
		
		public function guardarRespuesta(){
			$response = null;			
			try {	
				$sql = "INSERT INTO calificacion_ayuda (id, util, idCliente)VALUES ($this->id, $this->util, $this->idCliente)";
				$conn=new conexion();
				$conn->getConexion()->query($sql);
				
				$response = array(
					"SERVICIO"=>"CONECTADO",
					"ESTADO"=>"CREADO"
				);
			} catch(PDOException $e) {
				$response = array(
					"SERVICIO"=>"DESCONECTADO",
					"ESTADO"=>"NO CREADO",
					"ERROR"=>$e
				);
			}
			$conn = null;
			//echo json_encode($response);
			echo json_encode($response);
		}
		
		public function obtenerInfo(){
			$response = null;
			$sql = "SELECT * FROM ayuda;";
			try {
				$conn=new conexion();
				$stmt = $conn->getConexion()->query($sql);
				$result = $stmt->setFetchMode(PDO::FETCH_ASSOC);
				while ($row = $stmt->fetch()) {
					$response[] = $row;
				}
				echo json_encode($response);
			} catch (PDOException $e) {
				$response = array(
					"estado"=>"FALLIDO",
					"direccion"=>"NULL"
				);
				echo json_encode($response);
			}
		}
		
	}
?>