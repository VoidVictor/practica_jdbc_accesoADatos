package com.ies.serpis.view;

import com.ies.serpis.model.Excursion;
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
        System.out.println("4. Alta excursión");
        System.out.println("5. Baja excursión");
        System.out.println("6. Modificar fecha de excursión");
        System.out.println("7. Finalizar excursión");
        System.out.println("8. Listar todas las excursiones junto con los profesores acompañantes");
        System.out.println("9. Listar todas las excursiones finalizadas entre dos fechas");
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

    private double leerDoble() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Introduce un número decimal válido: ");
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

    public double pedirDoble(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return leerDoble();
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

    public void listarExcursiones(List<Excursion> excursiones){
        System.out.println("-".repeat(37) + " LISTADO DE EXCURSIONES " + "-".repeat(38));
        if (excursiones.isEmpty()) {
            System.out.println("No hay excursiones en la lista");
        } else {
            System.out.printf("%-3s | %-10s | %-25s | %-10s | %-15s | %-8s | %-10s%n", "ID", "ID Grupo", "Destino", "Duración", "Fecha", "Importe", "Estado");
            System.out.println("-".repeat(99));
            for (Excursion excursion : excursiones){
                System.out.printf("%-3d | %-10d | %-25s | %-10d | %-15s | %-8s | %-10s%n",
                        excursion.getId_excursion(),
                        excursion.getId_grupo(),
                        excursion.getDestino(),
                        excursion.getDuracion(),
                        excursion.getFecha(),
                        excursion.getImporte(),
                        excursion.getEstado()
                );
            }
            System.out.println("-".repeat(99));
        }
    }

    public void listarExcursionesConProfesores(List<Excursion> excursiones) {
        System.out.println("-".repeat(30) + " EXCURSIONES Y PROFESORES ACOMPAÑANTES " + "-".repeat(30));
        if (excursiones.isEmpty()) {
            System.out.println("No hay excursiones registradas");
        } else {
            for (Excursion excursion : excursiones) {
                System.out.println(excursion);
                for (Profesor profesor : excursion.getProfesores()) {
                    System.out.println("     · " + profesor.getNombre() + " " + profesor.getApellidos());
                }
                System.out.println("-".repeat(99));
            }
        }

    }

}
