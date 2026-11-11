package com.ies.serpis.service;

import com.ies.serpis.dao.ExcursionDaoMySQL;
import com.ies.serpis.dao.ResenyaDaoMongo;
import com.ies.serpis.exceptions.ExcursionInvalidaException;
import com.ies.serpis.exceptions.IdInvalidoException;
import com.ies.serpis.exceptions.ResenyaInvalidaException;
import com.ies.serpis.exceptions.ValoracionInvalidaException;
import com.ies.serpis.model.Actividad;
import com.ies.serpis.model.Resenya;

import java.util.List;


public class ResenyaService {

    private ResenyaDaoMongo daoResenya = new ResenyaDaoMongo();
    private ExcursionDaoMySQL daoExcursion = new ExcursionDaoMySQL();

    public Integer altaResenya(Resenya resenya) {
        validarExcursionParaResenya(resenya.getId_excursion());
        validarResenya(resenya);
        return daoResenya.crear(resenya);
    }

    public Resenya consultarResenya (int id_excursion){
        if (id_excursion <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        return daoResenya.encontrarPorID(id_excursion)
                .orElseThrow(() -> new ResenyaInvalidaException("No existe ninguna reseña con ID " + id_excursion));
    }

    public boolean modificarResenya (int id_excursion, Resenya resenya){
        if (id_excursion <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        Resenya original = daoResenya.encontrarPorID(id_excursion)
                .orElseThrow(() -> new ResenyaInvalidaException("No se puede modificar porque esa reseña no existe."));
        if (id_excursion != resenya.getId_excursion()) throw new IdInvalidoException("No se puede modificar el ID de la excursión.");
        if (!original.getFechaCreacion().equals(resenya.getFechaCreacion()))
            throw new ResenyaInvalidaException("No se puede modificar la fecha de creación.");
        validarResenya(resenya);
        return daoResenya.actualizar(resenya);
    }

    public boolean borrarResenya (int id_excursion){
        if (id_excursion <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        if (daoResenya.encontrarPorID(id_excursion).isEmpty()) throw new ResenyaInvalidaException("La reseña no existe.");
        return daoResenya.borrar(id_excursion);
    }

    public List<Resenya> listarResenyas(){
        return daoResenya.encontrarTodos();
    }

    public List<Resenya> buscarPorValoracionGlobal (int valoracion){
        if (valoracion < 1 || valoracion > 5) throw new ValoracionInvalidaException("La valoración global debe ser entre 1 y 5.");
        return daoResenya.buscarPorValoracionGlobal(valoracion);
    }

    public List<Resenya> buscarPorValoracionActividades (int valoracion){
        if (valoracion < 1 || valoracion > 5) throw new ValoracionInvalidaException("La valoración de la actividad debe ser entre 1 y 5.");
        return daoResenya.buscarPorValoracionActividades(valoracion);
    }

    public List<Resenya> buscarConIncidencias(){
        return daoResenya.buscarConIncidencias();
    }


    // ------- MÉTODOS AUXILIARES --------

    public void validarExcursionParaResenya(int id_excursion) {
        if (id_excursion <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        if (!daoExcursion.existeExcursion(id_excursion)) throw new ExcursionInvalidaException("La excursión no existe con ese ID.");
        if (!daoExcursion.estaFinalizada(id_excursion)) throw new ExcursionInvalidaException("La excursión no está finalizada.");
        if (daoResenya.encontrarPorID(id_excursion).isPresent()) throw new ResenyaInvalidaException("La excursión ya tiene reseña.");
    }

    private void validarResenya(Resenya resenya) {
        if (resenya.getValoracionGlobal() < 1 || resenya.getValoracionGlobal() > 5) throw new ValoracionInvalidaException("La valoración global debe ser entre 1 y 5.");
        if (resenya.getActividades() == null || resenya.getActividades().isEmpty()) throw new ResenyaInvalidaException("Debe haber al menos una actividad.");
        for (Actividad actividad : resenya.getActividades()){
            if (actividad.getNombre() == null || actividad.getNombre().isBlank()) throw new ResenyaInvalidaException("Hay una o más actividades que no tienen nombre.");
            if (actividad.getValoracion() < 1 || actividad.getValoracion() > 5) throw new ValoracionInvalidaException("La valoración de la actividad debe ser entre 1 y 5.");
        }
        for (String incidencia : resenya.getIncidencias()){
            if (incidencia == null || incidencia.isBlank()) throw new ResenyaInvalidaException("No puede haber una incidencia en blanco o vacía.");
        }
    }
}
