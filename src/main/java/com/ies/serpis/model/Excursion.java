package com.ies.serpis.model;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Excursion {

    private int id_excursion, id_grupo;
    private String destino;
    private int duracion;
    private String fecha;
    private double importe;
    private String estado;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private List<Profesor> profesores = new ArrayList<>();

    public Excursion(int id_excursion, int id_grupo, String destino, int duracion, String fecha, double importe, String estado) {
        this.id_excursion = id_excursion;
        this.id_grupo = id_grupo;
        this.destino = destino;
        this.duracion = duracion;
        this.fecha = fecha;
        this.importe = importe;
        this.estado = estado;
    }

    public Excursion(int id_grupo, String destino, int duracion, String fecha, double importe, String estado) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) { this.fecha = fecha; }

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

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    @Override
    public String toString() {
        return "[ID " + id_excursion + "] " + destino + " · " + fecha + " · " + duracion + " h · "
                + String.format("%.2f €", importe) + " · " + estado;
    }
}
