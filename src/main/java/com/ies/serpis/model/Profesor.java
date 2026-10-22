package com.ies.serpis.model;

public class Profesor {

    private int id_profesor, anyoNacimiento;
    private String nombre, apellidos, telefono;

    public Profesor(int id_profesor, String nombre, String apellidos, int anyoNacimiento, String telefono) {
        this.id_profesor = id_profesor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.anyoNacimiento = anyoNacimiento;
        this.telefono = telefono;
    }

    public Profesor(String nombre, String apellidos, int anyoNacimiento, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.anyoNacimiento = anyoNacimiento;
        this.telefono = telefono;
    }

    public Profesor() {
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public int getAnyoNacimiento() {
        return anyoNacimiento;
    }

    public void setAnyoNacimiento(int anyoNacimiento) {
        this.anyoNacimiento = anyoNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "[ID " + id_profesor + "] " + nombre + " " + apellidos
                + " · Nacimiento: " + anyoNacimiento + " · Teléfono: " + telefono;
    }
}
