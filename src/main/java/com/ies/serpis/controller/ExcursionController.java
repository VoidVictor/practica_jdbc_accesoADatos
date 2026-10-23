package com.ies.serpis.controller;

import com.ies.serpis.service.ExcursionService;
import com.ies.serpis.service.GrupoService;
import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.view.ConsolaView;

import java.util.ArrayList;
import java.util.List;

public class ExcursionController {

    private final ConsolaView view;
    private final ExcursionService serviceExcursion;
    private final GrupoService serviceGrupo;
    private final ProfesorService serviceProfesor;

    public ExcursionController(ConsolaView view, ExcursionService serviceExcursion, GrupoService serviceGrupo, ProfesorService serviceProfesor) {
        this.view = view;
        this.serviceExcursion = serviceExcursion;
        this.serviceGrupo = serviceGrupo;
        this.serviceProfesor = serviceProfesor;
    }

    public void crearExcursion() {
        view.listarGrupos(serviceGrupo.listarGrupos());
        int id_grupo = view.pedirEntero("Indica el ID del grupo al que deseas asignar la excursión");
        String destino = view.pedirString("Destino");
        int duracion = view.pedirEntero("Duración (en horas enteras)");
        String fecha = view.pedirString("Fecha (formato dd/MM/yyyy)");
        double importe = view.pedirDoble("Importe");
        List<Integer> IDsProfesoresAcompanyantes = profesoresAcompanyantes();

        int id = serviceExcursion.crearExcursion(id_grupo, destino, duracion, fecha, importe, IDsProfesoresAcompanyantes);
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

    public void finalizarExcursion(){
        view.listarExcursiones(serviceExcursion.listarExcursiones());
        int id_excursion = view.pedirEntero("Dime el ID de la excursión que quieres finalizar");
        view.info(serviceExcursion.finalizarExcursion(id_excursion)?"La excursión ha finalizado.":"No ha sido posible finalizar la excursión. Recuerda que no se pueden finalizar excursiones futuras.");
    }

    private List<Integer> profesoresAcompanyantes() {
        view.listarProfesores(serviceProfesor.listarProfesores());
        List<Integer> listaIDsProfesores = new ArrayList<>();
        view.info("A continuación deberás indicar los IDs de los profesores que acudirán a la excursión.\nIntroduce 0 para salir.");
        while (true) {
            int id = view.pedirEntero("ID del profesor");
            if (id == 0){
                break;
            }
            listaIDsProfesores.add(id);
        }
        return listaIDsProfesores;
    }

    public void listarExcursionesConProfesores() {
        view.listarExcursionesConProfesores(serviceExcursion.listarExcursionesConProfesores());
    }

}
