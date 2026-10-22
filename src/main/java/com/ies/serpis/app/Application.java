package com.ies.serpis.app;

import com.ies.serpis.controller.Controller;
import com.ies.serpis.controller.ExcursionController;
import com.ies.serpis.controller.GrupoController;
import com.ies.serpis.controller.ProfesorController;
import com.ies.serpis.dao.ProfesorDaoJdbc;
import com.ies.serpis.service.ExcursionService;
import com.ies.serpis.service.GrupoService;
import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.view.ConsolaView;

import java.security.Provider;

public class Application {
    public static void main(String[] args) {
        ConsolaView view = new ConsolaView();

        ProfesorService profesorService = new ProfesorService();
        ProfesorController profesorController = new ProfesorController(view, profesorService);

        GrupoService grupoService = new GrupoService();
        GrupoController grupoController = new GrupoController(view, grupoService);

        ExcursionService excursionService = new ExcursionService();
        ExcursionController excursionController = new ExcursionController(view, excursionService, grupoService);

        Controller controller = new Controller(view, profesorController, grupoController, excursionController);
        controller.run();
    }
}
