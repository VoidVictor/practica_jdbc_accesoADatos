package com.ies.serpis.controller;

import com.ies.serpis.service.ProfesorService;
import com.ies.serpis.view.ConsolaView;


public class ProfesorController {

    private final ProfesorService service;
    private final ConsolaView view;

    public ProfesorController(ProfesorService service, ConsolaView view) {
        this.service = service;
        this.view = view;
    }

    public void crearProfesor() {
        String nombre = view.pedirString("Nombre");
        String apellidos = view.pedirString("Apellidos");
        int anyo_nacimiento = view.pedirEntero("Año de nacimiento");
        String telefono = view.pedirString("Teléfono (recuerda poner el prefijo internacional)");

        Integer id = service.crearProfesor(nombre, apellidos, anyo_nacimiento, telefono);
        view.info("Profesor creado con id " + id);
    }

    public void listarProfesores(){
        view.listarProfesores(service.listarProfesores());
    }



}
