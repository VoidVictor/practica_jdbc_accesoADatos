package com.ies.serpis.controller;

import com.ies.serpis.exceptions.ReglaNegocioException;
import com.ies.serpis.view.ConsolaView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {

    private final ConsolaView view;
    private final ProfesorController profesorController;
    private final GrupoController grupoController;
    private final ExcursionController excursionController;
    private final ResenyaController resenyaController;

    public Controller(ConsolaView view, ProfesorController profesorController, GrupoController grupoController, ExcursionController excursionController, ResenyaController resenyaController) {
        this.view = view;
        this.profesorController = profesorController;
        this.grupoController = grupoController;
        this.excursionController = excursionController;
        this.resenyaController = resenyaController;
    }

    public void run() {
        int op;
        do {
            op = view.menu();
            try {
                switch (op) {
                    case 1 -> profesorController.crearProfesor();
                    case 2 -> grupoController.crearGrupo();
                    case 3 -> grupoController.borrarGrupoPorId();
                    case 4 -> excursionController.crearExcursion();
                    case 5 -> excursionController.borrarExcursion();
                    case 6 -> excursionController.actualizarExcursion();
                    case 7 -> excursionController.finalizarExcursion();
                    case 8 -> excursionController.listarExcursionesConProfesores();
                    case 9 -> excursionController.listarFinalizadasEntreFechas();
                    case 10 -> runResenyas();
                    case 0 -> view.info(mostrarDespedida());
                    default -> view.error("Opción inválida, inténtalo de nuevo.");
                }
            } catch (ReglaNegocioException e) {
                view.error(e.getMessage());
            } catch (RuntimeException e) {
                view.error("Error técnico: " + e.getMessage());
            }
        } while (op != 0);
    }

    public void runResenyas() {
        int op;
        do {
            op = view.menuResenyas();
            try {
                switch (op) {
                    case 1 -> resenyaController.altaResenya();
                    case 2 -> resenyaController.consultarResenya();
                    case 3 -> resenyaController.modificarResenya();
                    case 4 -> resenyaController.eliminarResenya();
                    case 5 -> resenyaController.listarResenyas();
                    case 6 -> resenyaController.buscarPorValoracionGlobal();
                    case 7 -> resenyaController.buscarPorValoracionActividades();
                    case 8 -> resenyaController.listarResenyasConIncidencias();
                    case 0 -> view.info("Volviendo al menú principal...");
                    default -> view.error("Opción inválida, inténtalo de nuevo.");
                }
            } catch (ReglaNegocioException e) {
                view.error(e.getMessage());
            } catch (RuntimeException e) {
                view.error("Error técnico: " + e.getMessage());
            }
        } while (op != 0);
    }

    private String mostrarDespedida() {
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "¡Adiós! Sesión finalizada a las " + hora + " del día " + fecha;
    }
}
