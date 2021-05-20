<?php
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
		public function getConexion(){
			return $this->conexion;
		}
	
		public function guardarCliente(){
			
				/*if ( !file_exists("clientes_ejemplo.json") ) {
					throw new Exception('File not found.');
				}	
				$archivo_json=file_get_contents("clientes_ejemplo.json");
				$clientes=null;
				$clientes=json_decode($archivo_json, true);
				$clientes[]=array(
					"id"=>getId(),
					"nombres"=>$this->nombres,
					"apellidos"=>$this->apellidos,
					"correo"=>$this->correo ,
					"edad"=>$this->edad,
					"clave"=>$this->clave
				);
				
				$archivo=fopen("clientes_ejemplo.json", "w");
				if ( !$archivo ) {
					throw new Exception('File open failed.');
				}  
				fwrite($archivo, json_encode($clientes));
				fclose($archivo);
				*/
				
		 
			
		}
		
		public function actualizarCliente(){
				
		}
		
		public function borrarCliente(){
				
		}
		
		public function mostrarCliente(){
				
		}
	}
	
?>	