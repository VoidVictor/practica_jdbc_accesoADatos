package com.ies.serpis.controller;

import com.ies.serpis.service.ExcursionService;
import com.ies.serpis.service.GrupoService;
import com.ies.serpis.view.ConsolaView;

public class ExcursionController {

    private final ConsolaView view;
    private final ExcursionService serviceExcursion;
    private final GrupoService serviceGrupo;

    public ExcursionController(ConsolaView view, ExcursionService serviceExcursion, GrupoService serviceGrupo) {
        this.view = view;
        this.serviceExcursion = serviceExcursion;
        this.serviceGrupo = serviceGrupo;
    }

    public void crearExcursion() {
        view.listarGrupos(serviceGrupo.listarGrupos());
        int id_grupo = view.pedirEntero("Indica el ID del grupo al que deseas asignar la excursión");
        String destino = view.pedirString("Destino");
        int duracion = view.pedirEntero("Duración (en horas enteras)");
        String fecha = view.pedirString("Fecha (formato dd/MM/yyyy)");
        double importe = view.pedirDoble("Importe");

        int id = serviceExcursion.crearExcursion(id_grupo, destino, duracion, fecha, importe);
        view.info("Excursión creada con el ID " + id);
    }

    public void borrarExcursion(){
        view.listarExcursiones(serviceExcursion.listarExcursiones());
        int id_excursion = view.pedirEntero("Indica el ID de la excursión que deseas borrar");
        view.info("Excursión seleccionada: " + serviceExcursion.obtenerExcursionPorID(id_excursion));
        String decision = view.pedirString("¿Estás seguro/a de que deseas eliminar la excursión? (s/n)");
        if (decision.equalsIgnoreCase("s")) {
            view.info(serviceExcursion.borrarExcursion(id_excursion)? "Excursión eliminada correctamente":"No se ha podido eliminar la excursión");
        }
    }

    public void actualizarExcursion(){
        view.listarExcursiones(serviceExcursion.listarExcursiones());
        int id_excursion = view.pedirEntero("Dime el ID de la excursión que quieres modificar la fecha");
        String fecha = view.pedirString("Dime la nueva fecha (dd/MM/yyyy)");
        view.info(serviceExcursion.modificarExcursion(id_excursion, fecha)?"La fecha se ha modificado correctamente":"No se ha podido modificar la fecha");
    }
}
