package com.ies.serpis.view;

import com.ies.serpis.model.Grupo;
import com.ies.serpis.model.Profesor;

import java.util.List;
import java.util.Scanner;

public class ConsolaView {

    private final Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("\n=== GESTIÓN DE EXCURSIONES DEL IES SERPIS ===");
        System.out.println("1. Alta profesor");
        System.out.println("2. Alta grupo");
        System.out.println("3. Baja grupo");
        System.out.println("0. Salir");
        System.out.println("=============================================");
        System.out.print("Elige una opción: ");
        return leerEntero();
    }

    private int leerEntero() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Introduce un número entero válido: ");
            }
        }
    }

    public String pedirString(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return sc.nextLine().trim();
    }

    public int pedirEntero(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return leerEntero();
    }

    public void info(String msg) {
        System.out.println(msg);
    }

    public void error(String msg) {
        System.err.println("ERROR: " + msg);
    }

    public void listarProfesores(List<Profesor> profesores){
        System.out.println("-".repeat(26) + " LISTADO DE PROFESORES " + "-".repeat(26));
        if (profesores.isEmpty()) {
            System.out.println("No hay profesores en la lista");
        } else {
            System.out.printf("%-3s | %-10s | %-20s | %-15s | %-15s%n", "ID", "Nombre", "Apellidos", "Año Nacimiento", "Teléfono");
            System.out.println("-".repeat(75));
            for (Profesor profesor : profesores){
                System.out.printf("%-3d | %-10s | %-20s | %-15d | %-15s%n",
                        profesor.getId_profesor(),
                        profesor.getNombre(),
                        profesor.getApellidos(),
                        profesor.getAnyoNacimiento(),
                        profesor.getTelefono()
                );
            }
            System.out.println("-".repeat(75));
        }
    }

    public void listarGrupos(List<Grupo> grupos){
        System.out.println("-".repeat(26) + " LISTADO DE GRUPOS " + "-".repeat(26));
        if (grupos.isEmpty()) {
            System.out.println("No hay grupos en la lista");
        } else {
            System.out.printf("%-3s | %-10s | %-20s | %-15s%n", "ID", "Curso", "Etapa educativa", "Nº de alumnos");
            System.out.println("-".repeat(75));
            for (Grupo grupo : grupos){
                System.out.printf("%-3d | %-10s | %-20s | %-15d%n",
                        grupo.getId_grupo(),
                        grupo.getCurso(),
                        grupo.getEtapaEducativa(),
                        grupo.getCantidadAlumnos()
                );
            }
            System.out.println("-".repeat(75));
        }
    }



}
