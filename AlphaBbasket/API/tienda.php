<?php
	class Tienda{
		var $id;
		var $nombre;
		var $horaApertura;
		var $horaCierre;
		var $idCliente;
		
		public function __construct ($id, $nombre, $horaApertura, $horaCierre, $idCliente){
			$this->id = $id;
			$this->nombre = $nombre;
			$this->horaApertura = $horaApertura;
			$this->horaCierre = $horaCierre;
			$this->idCliente = $idCliente;
		}
		
		public function guardarTienda(){
			$response = null;			
			try {	
				$sql = "INSERT INTO tienda (id, nombre, horaApertura, horaCierre, idCliente)VALUES ($this->id, $this->nombre, $this->horaApertura, $this->horaCierre, $this->idCliente)";
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
	}
?>