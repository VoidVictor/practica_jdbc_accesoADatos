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

    public Controller(ConsolaView view, ProfesorController profesorController, GrupoController grupoController) {
        this.view = view;
        this.profesorController = profesorController;
        this.grupoController = grupoController;
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
                    case 0 -> view.info(mostrarDespedida());
                    default -> view.error("Opción inválida, inténtalo de nuevo.");
                }
            } catch (ReglaNegocioException e) {
                view.error(e.getMessage());
            }
        } while (op != 0);
    }

    private String mostrarDespedida() {
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "¡Adiós! Sesión finalizada a las " + hora + " del día " + fecha;
    }
}
