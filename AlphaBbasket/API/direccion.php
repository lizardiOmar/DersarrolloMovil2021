<?php
	require_once 'conexion.php';
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
				$conn=new Conexion();
				$conn->getConexion()->exec($sql);
				
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
		
		public static function mostrarDireccion($indice){
			$response = null;
			$sql = "SELECT * FROM direccion WHERE id=$indice;";
			try {
				$conn=new Conexion();
				$stmt = $conn->getConexion()->query($sql);
				$result = $stmt->setFetchMode(PDO::FETCH_NUM);
				while ($row = $stmt->fetch()) {
					$id = $row[0];
					$latitud = $row[1];
					$longitud = $row[2];
					$altura = $row[3];
					$correo = $row[4];
				}
				$direccion =  new Direccion("$id", "$latitud", "$longitud", "$altura", "$correo");
				$response = array(
					"direccion"=>$direccion
				);
				echo json_encode($response);
				//echo $id . $latitud . $longitud . $altura . $correo . "Hecho!";
				
			} catch (PDOException $e) {
				$response = array(
					"estado"=>"FALLIDO",
					"direccion"=>"NULL"
				);
				echo json_encode($response);
			}
		}
		
		public static function actualizarDireccion($indice, $latitud, $longitud, $altura){
			$response = null;
			$sql = "UPDATE direccion SET latitud='$latitud', longitud='$longitud', altura='$altura' WHERE id=$indice;";
			try {
				$conn=new Conexion();
				$stmt = $conn->getConexion()->query($sql);
				echo "Direccion actualizada!";
			}catch(PDOException $e) {
					$response = array(
						"SERVICIO"=>"DESCONECTADO",
						"ESTADO"=>"NO CREADO",
						"ERROR"=>$e
					);
			}
		}
		
		public static function borrarDireccion($correo){
			$response = null;
			$sql = "DELETE FROM direccion WHERE correo='$correo';";
			try {
				$conn=new Conexion();
				$stmt = $conn->getConexion()->query($sql);
				$response = array(
					"estado"=>"TRUE",
					"MENSAJE"=>"Cuenta eliminada"
				);
			} catch (PDOException $e) {
				$response = array(
					"estado"=>"FALSE",
					"MENSAJE"=>$e
				);
			}
			
			echo json_encode($response);
		}
	}
?>	