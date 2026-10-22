package com.ies.serpis.model;

public class Grupo {

    private int id_grupo, cantidadAlumnos;
    private String curso, etapaEducativa;

    public Grupo(int id_grupo, String curso, String etapaEducativa, int cantidadAlumnos) {
        this.id_grupo = id_grupo;
        this.curso = curso;
        this.etapaEducativa = etapaEducativa;
        this.cantidadAlumnos = cantidadAlumnos;
    }

    public Grupo(String curso, String etapaEducativa, int cantidadAlumnos) {
        this.curso = curso;
        this.etapaEducativa = etapaEducativa;
        this.cantidadAlumnos = cantidadAlumnos;
    }

    public Grupo() {
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEtapaEducativa() {
        return etapaEducativa;
    }

    public void setEtapaEducativa(String etapaEducativa) {
        this.etapaEducativa = etapaEducativa;
    }

    public int getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setCantidadAlumnos(int cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "ID = " + id_grupo +
                ", Curso = '" + curso + '\'' +
                ", Etapa educativa = '" + etapaEducativa + '\'' +
                ", Cantidad de alumnos = " + cantidadAlumnos +
                '}';
    }
}
