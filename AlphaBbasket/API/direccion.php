<?php
	class Direccion{
		var $id;
		var $latitud;
		var $longitud;
		var $altura;
		var $correo;
		
		public function __construct($id, $latitud, $longitud, $altura, $correo){
			$this->id 			= $id;
			$this->latitud 		= $latitud;
			$this->longitud 	= $longitud;
			$this->altura 		= $altura;
			$this->correo		= $correo;
		}
		//CRUD
		public function getId(){
			if(isset($this->id)){
				return $this->id;
			}
		}
		public function getLatitud(){
			if(isset($this->latitud)){
				return $this->latitud;
			}
		}
		public function getLongitud(){
			if(isset($this->longitud)){
				return $this->longitud;
			}
		}
		public function getAltura(){
			if(isset($this->altura)){
				return $this->altura;
			}
		}
		public function getCorreo(){
			if(isset($this->correo)){
				return $this->correo;
			}
		}
		public function __toString(){
			echo $id . " " . $latitud . " " . $longitud . " " . $altura . " " . $correo;
		}
		
		public function guardarDireccion(){
			$response = null;			
			try {	
				$sql = "INSERT INTO direccion (id, latitud, longitud, altura, correo)VALUES ($this->id, $this->latitud, $this->longitud, $this->altura, '$this->correo')";
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
		
		public static function actualizarDireccion($correo, $latitud, $longitud, $altura){
			$response = null;
			$email=$correo;
			$lat=$latitud;
			$lon=$longitud;
			$alt=$altura;
			$sql = "UPDATE direccion SET latitud=$lat, longitud=$lon, altura=$alt WHERE correo='$correo';";
			try {
				$conn=new conexion();
				$conn->getConexion()->query($sql);
				$response = array(
					"Respuesta"=>"Coordenadas actualizadas."
				);
			}catch(PDOException $e) {
					$response = array(
						"SERVICIO"=>"DESCONECTADO",
						"ESTADO"=>"NO CREADO",
						"ERROR"=>$e
					);
			}
			echo json_encode($response);
			return json_encode($response);
		}
		
		public static  function borrarDireccion($correo){
			$response = null;
			$sql = "DELETE FROM direccion WHERE correo='$correo';";
			try {
				$conn=new conexion();
				$stmt = $conn->getConexion()->query($sql);
				$response = array(
					"estado"=>"TRUE",
					"accion"=>"DELETE"
				);
				//echo json_encode($response);
			} catch (PDOException $e) {
				$response = array(
					"estado"=>"FALSE",
					"MENSAJE"=>$e
				);
				echo json_encode($response);
			}
			
		}
		
		public static function mostrarDireccion($correo){
			$response = null;
			$sql = "SELECT * FROM direccion WHERE correo='$correo';";
			try {
				$conn=new Conexion();
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
			return json_encode($response);
		}
	}
	
?>	