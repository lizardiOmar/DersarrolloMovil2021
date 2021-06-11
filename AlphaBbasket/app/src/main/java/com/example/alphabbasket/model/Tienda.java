package com.example.alphabbasket.model;

public class Tienda {
    private String id;
    private String nombreTienda;
    private String horarioEntrada;
    private String horarioSalida;
    private String idCliente;

    public Tienda(String id, String nombreTienda, String horarioEntrada, String horarioSalida, String idCliente) {
        this.id=id;
        this.nombreTienda = nombreTienda;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.idCliente = idCliente;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombreTienda() { return nombreTienda; }

    public void setNombreTienda(String nombreTienda) { this.nombreTienda = nombreTienda; }

    public String getHorarioEntrada() { return horarioEntrada; }

    public void setHorarioEntrada(String horarioEntrada) { this.horarioEntrada = horarioEntrada; }

    public String getHorarioSalida() { return horarioSalida; }

    public void setHorarioSalida(String horarioSalida) { this.horarioSalida = horarioSalida; }

    public String getIdCliente() { return idCliente; }

    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
}
