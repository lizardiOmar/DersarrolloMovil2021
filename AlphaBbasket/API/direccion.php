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
		public function guardarDireccion(){
			
		}
		
		public function actualizarDireccion(){
				
		}
		
		public function borrarDireccion(){
				
		}
		
		public function mostrarDireccion(){
				
		}
	}
	
?>	