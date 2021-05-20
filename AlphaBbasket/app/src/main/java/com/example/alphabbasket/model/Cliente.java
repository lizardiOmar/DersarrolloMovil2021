package com.example.alphabbasket.model;


public class Cliente {
    private String  id, nombres, apellidos, correo, contrasena, edad;
    public Cliente(String id, String nombres, String apellidos, String correo, String edad, String contrasena){
        this.id=id;
        this.nombres=nombres;
        this.apellidos= apellidos;
        this.correo=correo;
        this.edad=edad;
        this.contrasena=contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String  getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
