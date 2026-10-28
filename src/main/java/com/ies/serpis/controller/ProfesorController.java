package com.ies.serpis.controller;

import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.view.ConsolaView;


public class ProfesorController {

    private final ProfesorService service;
    private final ConsolaView view;

    public ProfesorController(ConsolaView view, ProfesorService service) {
        this.view = view;
        this.service = service;
    }

    public void crearProfesor() {
        String nombre = view.pedirString("Nombre");
        String apellidos = view.pedirString("Apellidos");
        int anyoNacimiento = view.pedirEntero("Año de nacimiento");
        String telefono = view.pedirString("Teléfono (recuerda poner el prefijo internacional)");

        Integer id = service.crearProfesor(nombre, apellidos, anyoNacimiento, telefono);
        view.info("Profesor creado con id " + id);
    }
}
