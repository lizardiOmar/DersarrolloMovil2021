package com.example.alphabbasket.model;

public class Direccion {

    private String correo, id,latitud, longitud, altura;

    public Direccion(String id, String latitud, String longitud, String altura, String correo) {
        this.correo = correo;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altura = altura;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }
}
