package com.ies.serpis.app;

import com.ies.serpis.controller.Controller;
import com.ies.serpis.controller.ProfesorController;
import com.ies.serpis.dao.ProfesorDaoJdbc;
import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.view.ConsolaView;

import java.security.Provider;

public class Application {
    public static void main(String[] args) {
        ConsolaView view = new ConsolaView();

        ProfesorService profesorService = new ProfesorService();
        ProfesorController profesorController = new ProfesorController(profesorService, view);

        Controller controller = new Controller(view, profesorController);
        controller.run();
    }
}
