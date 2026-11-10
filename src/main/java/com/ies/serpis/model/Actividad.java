package com.ies.serpis.model;

public class Actividad {

    private String nombre;
    private int valoracion;

    public Actividad(String nombre, int valoracion) {
        this.nombre = nombre;
        this.valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "nombre='" + nombre + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
