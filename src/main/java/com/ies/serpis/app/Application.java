package com.ies.serpis.app;

import com.ies.serpis.controller.*;
import com.ies.serpis.service.ExcursionService;
import com.ies.serpis.service.GrupoService;
import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.service.ResenyaService;
import com.ies.serpis.view.ConsolaView;

public class Application {
    public static void main(String[] args) {
        ConsolaView view = new ConsolaView();

        ProfesorService profesorService = new ProfesorService();
        ProfesorController profesorController = new ProfesorController(view, profesorService);

        GrupoService grupoService = new GrupoService();
        GrupoController grupoController = new GrupoController(view, grupoService);

        ExcursionService excursionService = new ExcursionService();
        ExcursionController excursionController = new ExcursionController(view, excursionService, grupoService, profesorService);

        ResenyaService resenyaService = new ResenyaService();
        ResenyaController resenyaController = new ResenyaController(view, resenyaService, excursionService);

        Controller controller = new Controller(view, profesorController, grupoController, excursionController, resenyaController);
        controller.run();
    }
}
