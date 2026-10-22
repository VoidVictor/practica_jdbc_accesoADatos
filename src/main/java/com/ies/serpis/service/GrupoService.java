package com.ies.serpis.service;

import com.ies.serpis.dao.GrupoDaoJdbc;
import com.ies.serpis.exceptions.*;
import com.ies.serpis.model.Grupo;
import com.ies.serpis.model.Profesor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GrupoService {

    private final GrupoDaoJdbc dao = new GrupoDaoJdbc();

    public Integer crearGrupo (String curso, String etapaEducativa, int cantidadAlumnos){

        curso = validarCursoYFormatear(curso);
        etapaEducativa = validarEtapaYFormatear(etapaEducativa);
        validarCampos(cantidadAlumnos);

        return dao.crear(new Grupo(curso, etapaEducativa, cantidadAlumnos));
    }

    public List<Grupo> listarGrupos() {
        return dao.encontrarTodos();
    }

    public Optional<Grupo> obtenerGrupoPorID (Integer id){
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1");
        return dao.encontrarPorID(id);
    }

    public boolean borrarGrupo(Integer id){
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1");
        return dao.borrar(id);
    }

    // ---------- MÉTODOS AUXILIARES ----------

    private String validarEtapaYFormatear (String etapaEducativa){
        if (etapaEducativa == null || etapaEducativa.isBlank())throw new CampoObligatorioException("Es obligatorio poner la etapa educativa (ESO, Bachillerato o FP).");
        etapaEducativa = etapaEducativa.toUpperCase();
        return switch (etapaEducativa) {
            case "ESO" -> "ESO";
            case "BACHILLERATO" -> "Bachillerato";
            case "FP" -> "FP";
            default -> throw new EtapaInvalidaException("La etapa edcuativa debe ser ESO, Bachillerato o FP.");
        };
    }

    private String validarCursoYFormatear (String curso){
        if (curso == null || curso.isBlank())throw new CampoObligatorioException("Es obligatorio poner el curso.");
        return curso.toUpperCase();
    }

    private void validarCampos(int cantidad_alumnos) {
        if (cantidad_alumnos <= 0) throw new CantidadInvalidaException("Debe haber al menos 1 alumno en el grupo");
    }
}
