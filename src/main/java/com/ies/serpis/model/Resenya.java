package com.ies.serpis.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Resenya {
    private int id_excursion;
    private LocalDate fechaCreacion;
    private int valoracionGlobal;
    private String comentario;
    private List<Actividad> actividades;
    private List<String> incidencias;

    public Resenya(int id_excursion, int valoracionGlobal, String comentario, List<Actividad> actividades, List<String> incidencias) {
        this.id_excursion = id_excursion;
        this.fechaCreacion = LocalDate.now();
        this.valoracionGlobal = valoracionGlobal;
        this.comentario = comentario;
        this.actividades = actividades;
        if (incidencias == null) {
            this.incidencias = new ArrayList<>();
        } else {
            this.incidencias = incidencias;
        }
    }

    public Resenya(int id_excursion, LocalDate fechaCreacion, int valoracionGlobal, String comentario, List<Actividad> actividades, List<String> incidencias) {
        this.id_excursion = id_excursion;
        this.fechaCreacion = fechaCreacion;
        this.valoracionGlobal = valoracionGlobal;
        this.comentario = comentario;
        this.actividades = actividades;
        if (incidencias == null) {
            this.incidencias = new ArrayList<>();
        } else {
            this.incidencias = incidencias;
        }
    }

    public int getId_excursion() {
        return id_excursion;
    }

    public void setId_excursion(int id_excursion) {
        this.id_excursion = id_excursion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getValoracionGlobal() {
        return valoracionGlobal;
    }

    public void setValoracionGlobal(int valoracionGlobal) {
        this.valoracionGlobal = valoracionGlobal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<String> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<String> incidencias) {
        this.incidencias = incidencias;
    }

    @Override
    public String toString() {
        return "Resenya{" +
                "id=" + id_excursion +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", valoracion=" + valoracionGlobal +
                ", comentario='" + comentario + '\'' +
                ", actividades=" + actividades +
                ", incidencias=" + incidencias +
                '}';
    }
}
