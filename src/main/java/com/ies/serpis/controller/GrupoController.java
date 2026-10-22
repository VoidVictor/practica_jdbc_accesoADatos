package com.ies.serpis.controller;

import com.ies.serpis.service.GrupoService;
import com.ies.serpis.view.ConsolaView;

public class GrupoController {

    private final ConsolaView view;
    private final GrupoService service;

    public GrupoController(ConsolaView view, GrupoService service) {
        this.view = view;
        this.service = service;
    }

    public void crearGrupo() {
        String curso = view.pedirString("Curso");
        String etapaEducativa = view.pedirString("Etapa educativa (ESO/Bachillerato/FP)");
        int cantidadAlumnos = view.pedirEntero("Número de alumnos del curso");

        Integer id = service.crearGrupo(curso,etapaEducativa,cantidadAlumnos);
        view.info("Grupo creado con id " + id);
    }

    public void listarGrupos(){
        view.listarGrupos(service.listarGrupos());
    }

    public void borrarGrupoPorId() {
        listarGrupos();
        int id = view.pedirEntero("Indica el ID del grupo que deseas borrar");
        view.info("Grupo seleccionado: " + service.obtenerGrupoPorID(id));
        String decision = view.pedirString("¿Estás seguro/a de que deseas eliminar el grupo? (s/n)");
        if (decision.equalsIgnoreCase("s")) {
            view.info(service.borrarGrupo(id)? "Grupo eliminado correctamente":"No se ha podido eliminar el grupo");
        }
    }
}
