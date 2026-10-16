package com.ies.serpis.model;

public class Prof_Excu {

    private int id_profesor, id_excursion;

    public Prof_Excu(int id_profesor, int id_excursion) {
        this.id_profesor = id_profesor;
        this.id_excursion = id_excursion;
    }

    public Prof_Excu() {
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public int getId_excursion() {
        return id_excursion;
    }

    public void setId_excursion(int id_excursion) {
        this.id_excursion = id_excursion;
    }

    @Override
    public String toString() {
        return "Prof_Excu{" +
                "ID del profesor = " + id_profesor +
                ", ID de la excursión = " + id_excursion +
                '}';
    }
}
