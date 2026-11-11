package com.ies.serpis.view;

import com.ies.serpis.model.*;

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
        System.out.println("10. Gestionar reseñas de excursiones");
        System.out.println("0. Salir");
        System.out.println("=============================================");
        System.out.print("Elige una opción: ");
        return leerEntero();
    }

    public int menuResenyas() {
        System.out.println("\n=========== GESTIÓN DE RESEÑAS ===========");
        System.out.println("1. Alta de reseña");
        System.out.println("2. Consultar la reseña de una excursión");
        System.out.println("3. Modificar una reseña");
        System.out.println("4. Eliminar una reseña");
        System.out.println("5. Listar todas las reseñas");
        System.out.println("6. Buscar reseñas por valoración global");
        System.out.println("7. Buscar reseñas con actividades de valoración inferior a un valor");
        System.out.println("8. Listar reseñas con incidencias");
        System.out.println("0. Volver al menú principal");
        System.out.println("==========================================");
        System.out.print("Elige una opción: ");
        return leerEntero();
    }

    public int menuModificacionResenyas() {
        System.out.println("\n=========== MODIFICACIÓN DE RESEÑA ===========");
        System.out.println("1. Cambiar comentario");
        System.out.println("2. Cambiar valoración global");
        System.out.println("3. Añadir actividad");
        System.out.println("4. Modificar actividad");
        System.out.println("5. Eliminar actividad");
        System.out.println("6. Añadir incidencia");
        System.out.println("7. Modificar incidencia");
        System.out.println("8. Eliminar incidencia");
        System.out.println("0. Guardar y salir");
        System.out.println("==========================================");
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

    public void listarResenyas(List<Resenya> resenyas) {
        System.out.println("-".repeat(40) + " LISTADO DE RESEÑAS " + "-".repeat(39));
        if (resenyas.isEmpty()) {
            System.out.println("No hay reseñas registradas");
        } else {
            for (Resenya resenya : resenyas) {
                System.out.println("Excursión ID: " + resenya.getId_excursion()
                        + " · Fecha: " + resenya.getFechaCreacion().format(Excursion.FORMATO_FECHA)
                        + " · Valoración global: " + resenya.getValoracionGlobal() + "/5");
                System.out.println("Comentario: " + resenya.getComentario());

                System.out.println("Actividades:");
                for (Actividad actividad : resenya.getActividades()) {
                    System.out.println("     · " + actividad.getNombre() + " (" + actividad.getValoracion() + "/5)");
                }

                System.out.println("Incidencias:");
                if (resenya.getIncidencias().isEmpty()) {
                    System.out.println("     · Sin incidencias");
                } else {
                    for (String incidencia : resenya.getIncidencias()) {
                        System.out.println("     · " + incidencia);
                    }
                }
                System.out.println("-".repeat(99));
            }
        }
    }

    public void listarActividades(List<Actividad> actividades) {
        for (int i = 0; i < actividades.size(); i++) {
            System.out.println((i + 1) + ". " + actividades.get(i).getNombre()
                    + " (" + actividades.get(i).getValoracion() + "/5)");
        }
    }

    public void listarIncidencias(List<String> incidencias) {
        if (incidencias.isEmpty()) {
            System.out.println("Esta reseña no tiene incidencias.");
        } else {
            for (int i = 0; i < incidencias.size(); i++) {
                System.out.println((i + 1) + ". " + incidencias.get(i));
            }
        }
    }

}
