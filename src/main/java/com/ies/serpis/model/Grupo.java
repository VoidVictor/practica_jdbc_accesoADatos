package com.ies.serpis.model;

public class Grupo {

    private int id_grupo, cantidad_alumnos;
    private String curso, etapa_educativa;

    public Grupo(int id_grupo, String curso, String etapa_educativa, int cantidad_alumnos) {
        this.id_grupo = id_grupo;
        this.curso = curso;
        this.etapa_educativa = etapa_educativa;
        this.cantidad_alumnos = cantidad_alumnos;
    }

    public Grupo(String curso, String etapa_educativa, int cantidad_alumnos) {
        this.curso = curso;
        this.etapa_educativa = etapa_educativa;
        this.cantidad_alumnos = cantidad_alumnos;
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

    public String getEtapa_educativa() {
        return etapa_educativa;
    }

    public void setEtapa_educativa(String etapa_educativa) {
        this.etapa_educativa = etapa_educativa;
    }

    public int getCantidad_alumnos() {
        return cantidad_alumnos;
    }

    public void setCantidad_alumnos(int cantidad_alumnos) {
        this.cantidad_alumnos = cantidad_alumnos;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "ID = " + id_grupo +
                ", Curso = '" + curso + '\'' +
                ", Etapa educativa = '" + etapa_educativa + '\'' +
                ", Cantidad de alumnos = " + cantidad_alumnos +
                '}';
    }
}
