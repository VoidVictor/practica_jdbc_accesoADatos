package com.ies.serpis.model;

import java.time.LocalDate;

public class Excursion {

    private int id_excursion, id_grupo;
    private String destino;
    private int duracion;
    private LocalDate fecha;
    private double importe;
    private String estado;

    public Excursion(int id_excursion, int id_grupo, String destino, int duracion, LocalDate fecha, double importe, String estado) {
        this.id_excursion = id_excursion;
        this.id_grupo = id_grupo;
        this.destino = destino;
        this.duracion = duracion;
        this.fecha = fecha;
        this.importe = importe;
        this.estado = estado;
    }

    public Excursion(int id_grupo, String destino, int duracion, LocalDate fecha, double importe, String estado) {
        this.id_grupo = id_grupo;
        this.destino = destino;
        this.duracion = duracion;
        this.fecha = fecha;
        this.importe = importe;
        this.estado = estado;
    }

    public Excursion() {
    }

    public int getId_excursion() {
        return id_excursion;
    }

    public void setId_excursion(int id_excursion) {
        this.id_excursion = id_excursion;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "ID = " + id_excursion +
                ", ID del Grupo = " + id_grupo +
                ", Destino='" + destino + '\'' +
                ", Duración (en horas) = " + duracion +
                ", Fecha = " + fecha +
                ", Importe = " + importe +
                ", Estado = '" + estado + '\'' +
                '}';
    }
}
