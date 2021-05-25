<?php
	class conexion{
		
		var $servername;
		var $username;
		var $password;
		var $database;
		var $conn;
		var $estado_servidor;
		
		public function __construct(){
			$this->servername		= "localhost";
			$this->username 		= "root";
			$this->password 		= "";
			$this->database 	 	= "bbasket";
			$this->conexion			= null;
			$this->estado_servidor	= "desconectado"; 
			try {
				$this->conexion = new PDO("mysql:host=$this->servername;dbname=$this->database", $this->username, $this->password);
				$this->conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				$this->estado_servidor= "conectado";
			} catch(PDOException $e) {
				$this->conexion=null;
				$this->estado_servidor = "desconectado";
			}
		}
		public function getConexion(){
			if($this->conexion!=null){
				return $this->conexion;
			}
		}
		
	}
?>