package com.ies.serpis.model;

public class Profesor {

    private int id_profesor, anyo_nacimiento;
    private String nombre, apellidos, telefono;

    public Profesor(int id_profesor, String nombre, String apellidos, int anyo_nacimiento,  String telefono) {
        this.id_profesor = id_profesor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.anyo_nacimiento = anyo_nacimiento;
        this.telefono = telefono;
    }

    public Profesor(String nombre, String apellidos, int anyo_nacimiento, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.anyo_nacimiento = anyo_nacimiento;
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

    public int getAnyo_nacimiento() {
        return anyo_nacimiento;
    }

    public void setAnyo_nacimiento(int anyo_nacimiento) {
        this.anyo_nacimiento = anyo_nacimiento;
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
        return "Profesor{" +
                "ID = " + id_profesor +
                ", Nombre = " + nombre +
                ", Apellidos = '" + apellidos + '\'' +
                ", Año de nacimiento = '" + anyo_nacimiento + '\'' +
                ", Teléfono = '" + telefono + '\'' +
                '}';
    }
}
