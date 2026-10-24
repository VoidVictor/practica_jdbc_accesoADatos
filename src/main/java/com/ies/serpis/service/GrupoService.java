package com.ies.serpis.service;

import com.ies.serpis.dao.ExcursionDaoJdbc;
import com.ies.serpis.dao.GrupoDaoJdbc;
import com.ies.serpis.exceptions.*;
import com.ies.serpis.model.Grupo;

import java.util.List;

public class GrupoService {

    private final GrupoDaoJdbc dao = new GrupoDaoJdbc();
    private final ExcursionDaoJdbc daoExcursion = new ExcursionDaoJdbc();

    public Integer crearGrupo (String curso, String etapaEducativa, int cantidadAlumnos){

        curso = validarCursoYFormatear(curso);
        etapaEducativa = validarEtapaYFormatear(etapaEducativa);
        validarCampos(cantidadAlumnos);

        return dao.crear(new Grupo(curso, etapaEducativa, cantidadAlumnos));
    }

    public List<Grupo> listarGrupos() {
        return dao.encontrarTodos();
    }

    public Grupo obtenerGrupoPorID(Integer id) {
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        return dao.encontrarPorID(id)
                .orElseThrow(() -> new IdInvalidoException("No existe ningún grupo con ID " + id));
    }

    public boolean borrarGrupo(Integer id){
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1.");
        if (daoExcursion.existenExcursionesDeGrupo(id))
            throw new GrupoInvalidoException("No se puede eliminar el grupo porque tiene excursiones asociadas.");
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
            default -> throw new EtapaInvalidaException("La etapa educativa debe ser ESO, Bachillerato o FP.");
        };
    }

    private String validarCursoYFormatear (String curso){
        if (curso == null || curso.isBlank())throw new CampoObligatorioException("Es obligatorio poner el curso.");
        curso = curso.toUpperCase();
        if (dao.existeCurso(curso)) throw new CursoExistenteException("El grupo introducido ya existe.");
        return curso;
    }

    private void validarCampos(int cantidadAlumnos) {
        if (cantidadAlumnos <= 0) throw new CantidadInvalidaException("Debe haber al menos 1 alumno en el grupo");
    }
}
