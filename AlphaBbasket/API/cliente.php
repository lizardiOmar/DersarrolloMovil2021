<?php
	require_once 'conexion.php';
	class cliente{		
		var $id;
		var $nombres;
		var $apellidos;
		var $correo;
		var $edad;
		var $clave;
		
		public function __construct($id, $nombres, $apellidos, $correo, $edad, $clave){
			$this->id 			= $id;
			$this->nombres 		= $nombres;
			$this->apellidos 	= $apellidos;
			$this->correo 		= $correo;
			$this->edad			= $edad;
			$this->clave		= $clave; 	
		}
		
		//CRUD
		public function getId(){
			if(isset($this->id)){
				return $this->id;
			}
		}
		public function getNombres(){
			if(isset($this->nombres)){
				return $this->nombres;
			}
		}
		public function getApellidos(){
			if(isset($this->apellidos)){
				return $this->apellidos;
			}
		}
		public function getCorreo(){
			if(isset($this->correo)){
				return $this->correo;
			}
		}
		public function getClave(){
			if(isset($this->clave)){
				return $this->clave;
			}
		}
		public function getEdad(){
			if(isset($this->edad)){
				return $this->edad;
			}
		}
		
		public function guardarCliente(){
					
			try {	
				$sql = "INSERT INTO cliente (id, nombres, apellidos, correo, edad, clave, fecha_alta)
				VALUES ($this->id, '$this->nombres', '$this->apellidos', '$this->correo', $this->edad, '$this->clave', CURRENT_TIMESTAMP())";
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
			
			return json_encode($response);
		}
	
		public static function actualizarCliente($id, $dato, $index){
			$datos = array('nombres', 'apellidos', 'edad', 'clave');
			$columna = $datos[$index];
			$valor=$dato;
			$sql = "UPDATE cliente SET  $columna = '$dato' WHERE id = '$id'";
			try {	
				$conn=new Conexion();
				$conn->getConexion()->exec($sql);
				$response = array(
					"Respuesta"=>"Dato ($datos[$index]) actualizado.($valor)"
				);
				
			} catch(PDOException $e){
				$response = array(
					"SERVICIO"=>"DESCONECTADO",
					"ESTADO"=>"NO CREADO",
					"ERROR"=>$e,
					"Columna"=>$datos[$index],
					"Dato"=>$valor
				);
				
			}
			echo json_encode($response);
			return json_encode($response);
		}
		
		public static function borrarCliente($correo){
			$response = null;
			$sql = "DELETE FROM cliente WHERE correo='$correo'";
			$conn=new Conexion();
			try {
				$conn->getConexion()->exec($sql);
				
				if ($conn->getConexion()->query($sql) == TRUE) {
					$response = array(
						"estado"=>"TRUE",
						"MENSAJE"=>"Cuenta eliminada"
					);
				} else{
					$response = array(
						"estado"=>"FALSE",
						"MENSAJE"=>"Cuenta No eliminada"
					);
				}
			} catch (PDOException $e) {
				$response = array(
					"estado"=>"FALSE",
					"MENSAJE"=>$e
				);
			}
			$conn=null;
			return json_encode($response);
		}
		
		public static function mostrarCliente($correo){
			$response = null;
			$conn=new Conexion();
			$sql = "SELECT * FROM cliente where correo='$correo'";
			try {
				$stmt = $conn->getConexion()->query($sql);
				$result = $stmt->setFetchMode(PDO::FETCH_NUM);
				while ($row = $stmt->fetch()) {
					$cliente =  new Cliente($row[0], $row[1], $row[2], $row[3],  $row[4], $row[5]);
				}
				echo json_encode($cliente);
				return json_encode($cliente);
			} catch (PDOException $e) {
				$response = null;
				$response = array(
					"estado"=>"FALLIDO",
					"cliente"=>"NULL"
				);
				return json_encode($response);
			}
		}
	}
	
?>	