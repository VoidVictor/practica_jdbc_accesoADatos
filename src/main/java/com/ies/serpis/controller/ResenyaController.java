package com.ies.serpis.controller;

import com.ies.serpis.exceptions.ReglaNegocioException;
import com.ies.serpis.model.Actividad;
import com.ies.serpis.model.Excursion;
import com.ies.serpis.model.Resenya;
import com.ies.serpis.service.ExcursionService;
import com.ies.serpis.service.ResenyaService;
import com.ies.serpis.view.ConsolaView;

import java.util.ArrayList;
import java.util.List;

public class ResenyaController {

    private final ExcursionService serviceExcursion;
    private final ResenyaService serviceResenya;
    private final ConsolaView view;

    public ResenyaController(ConsolaView view, ResenyaService serviceResenya, ExcursionService serviceExcursion) {
        this.serviceResenya = serviceResenya;
        this.view = view;
        this.serviceExcursion = serviceExcursion;
    }

    public void altaResenya() {
        if (!mostrarExcursionesFinalizadas("dar de alta")) return;
        int id_excursion = view.pedirEntero("A qué excursión quieres añadir una reseña (Indica su ID)");
        serviceResenya.validarExcursionParaResenya(id_excursion);

        int valoracionGlobal = view.pedirEntero("Valoración global de la excursión (del 1 al 5)");
        String comentario = view.pedirString("Comentario");
        view.info("VALORACIÓN DE LAS ACTIVIDADES");
        int contador = 1;
        List<Actividad> actividades = new ArrayList<>();
        while (true) {
            String nombre = view.pedirString("Nombre de la actividad " + contador);
            int valoracion = view.pedirEntero("Valoración (del 1 al 5)");
            actividades.add(new Actividad(nombre, valoracion));
            contador++;
            if (view.pedirString("¿Añadir otra actividad? (s/n)").equalsIgnoreCase("n")) break;
        }
        List<String> incidencias = new ArrayList<>();
        if (view.pedirString("¿Quieres añadir alguna incidencia en la reseña sobre la excursión? (s/n)").equalsIgnoreCase("s")) {
            while (true) {
                String incidencia = view.pedirString("Incidencia");
                incidencias.add(incidencia);
                if (view.pedirString("¿Añadir otra incidencia? (s/n)").equalsIgnoreCase("n")) break;
            }
        }
        serviceResenya.altaResenya(new Resenya(id_excursion, valoracionGlobal, comentario, actividades, incidencias));
        view.info("¡Reseña creada correctamente!");
    }

    public void consultarResenya() {
        if (!mostrarExcursionesFinalizadas("consultar")) return;
        int id_excursion = view.pedirEntero("Qué reseña quieres consultar (Indica el ID de la excursión correspondiente)");
        view.info(serviceResenya.consultarResenya(id_excursion).toString());
    }

    public void modificarResenya() {
        if (!mostrarExcursionesFinalizadas("modificar")) return;
        int id_excursion = view.pedirEntero("Indica el ID de la excursión de la reseña que quieres modificar");
        Resenya resenya = serviceResenya.consultarResenya(id_excursion);
        boolean guardado = false;
        while (!guardado) {
            view.info("\n" + resenya);
            int op = view.menuModificacionResenyas();
            try {
                switch (op) {
                    case 1 -> resenya.setComentario(view.pedirString("Nuevo comentario"));
                    case 2 -> resenya.setValoracionGlobal(view.pedirEntero("Nueva valoración global (del 1 al 5)"));
                    case 3 -> {
                        String nombre = view.pedirString("Nombre de la actividad");
                        int valoracion = view.pedirEntero("Valoración (del 1 al 5)");
                        resenya.getActividades().add(new Actividad(nombre, valoracion));
                    }
                    case 4 -> {
                        if (resenya.getActividades().isEmpty()) {
                            view.info("No hay actividades para modificar.");
                        } else {
                            view.listarActividades(resenya.getActividades());
                            int posicion = pedirPosicion("Actividad que quieres modificar (indica el número)", resenya.getActividades().size());
                            String nombre = view.pedirString("Nuevo nombre de la actividad");
                            int valoracion = view.pedirEntero("Nueva valoración (del 1 al 5)");
                            resenya.getActividades().set(posicion, new Actividad(nombre, valoracion));
                        }
                    }
                    case 5 -> {
                        if (resenya.getActividades().isEmpty()) {
                            view.info("No hay actividades para eliminar.");
                        } else {
                            view.listarActividades(resenya.getActividades());
                            int posicion = pedirPosicion("Actividad que quieres eliminar (indica el número)", resenya.getActividades().size());
                            resenya.getActividades().remove(posicion);
                        }
                    }
                    case 6 -> resenya.getIncidencias().add(view.pedirString("Incidencia"));
                    case 7 -> {
                        if (resenya.getIncidencias().isEmpty()) {
                            view.info("No hay incidencias para modificar.");
                        } else {
                            view.listarIncidencias(resenya.getIncidencias());
                            int posicion = pedirPosicion("Incidencia que quieres modificar (indica el número)", resenya.getIncidencias().size());
                            resenya.getIncidencias().set(posicion, view.pedirString("Nueva incidencia"));
                        }
                    }
                    case 8 -> {
                        if (resenya.getIncidencias().isEmpty()) {
                            view.info("No hay incidencias para eliminar.");
                        } else {
                            view.listarIncidencias(resenya.getIncidencias());
                            int posicion = pedirPosicion("Incidencia que quieres eliminar (indica el número)", resenya.getIncidencias().size());
                            resenya.getIncidencias().remove(posicion);
                        }
                    }
                    case 0 -> {
                        serviceResenya.modificarResenya(id_excursion, resenya);
                        view.info("¡Reseña modificada correctamente!");
                        guardado = true;
                    }
                    default -> view.error("Opción inválida, inténtalo de nuevo.");
                }
            } catch (ReglaNegocioException e) {
                view.error(e.getMessage());
            } catch (RuntimeException e) {
                view.error("Error técnico: " + e.getMessage());
            }
        }
    }

    public void eliminarResenya() {
        if (!mostrarExcursionesFinalizadas("eliminar")) return;
        int id_excursion = view.pedirEntero("Indica el ID de la excursión de la reseña que quieres eliminar");
        view.info(serviceResenya.consultarResenya(id_excursion).toString());
        if (view.pedirString("¿Seguro/a de que quieres eliminar la reseña? (s/n)").equalsIgnoreCase("s")){
            if (serviceResenya.borrarResenya(id_excursion)) view.info("¡Reseña eliminada correctamente!");
            else view.info("No se ha podido eliminar la reseña.");
        } else {
            view.info("Eliminación cancelada.");
        }
    }

    public void listarResenyas(){
        view.listarResenyas(serviceResenya.listarResenyas());
    }

    public void buscarPorValoracionGlobal(){
        int valoracionGlobal = view.pedirEntero("Introduce la valoración global (del 1 al 5)");
        view.listarResenyas(serviceResenya.buscarPorValoracionGlobal(valoracionGlobal));
    }

    public void buscarPorValoracionActividades(){
        int valoracion = view.pedirEntero("Introduce la valoración (del 1 al 5)");
        view.listarResenyas(serviceResenya.buscarPorValoracionActividades(valoracion));
    }

    public void listarResenyasConIncidencias(){
        view.listarResenyas(serviceResenya.buscarConIncidencias());
    }

    // ------ MÉTODOS AUXILIARES ------

    private int pedirPosicion(String etiqueta, int tamanyo) {
        while (true) {
            int opcion = view.pedirEntero(etiqueta);
            if (opcion >= 1 && opcion <= tamanyo) {
                return opcion - 1;
            }
            view.error("Opción inválida. Indica un número de la lista.");
        }
    }

    private boolean mostrarExcursionesFinalizadas(String accion) {
        List<Excursion> excursionesFinalizadas = serviceExcursion.listarExcursionesFinalizadas();
        if (excursionesFinalizadas.isEmpty()) {
            view.info("No hay excursiones finalizadas para poder " + accion + " una reseña.");
            return false;
        }
        view.listarExcursiones(excursionesFinalizadas);
        return true;
    }
}