package com.ies.serpis.view;

import java.util.Scanner;

public class ConsolaView {

    Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("\n=== GESTIÓN DE EXCURSIONES DEL IES SERPIS ===");
        System.out.println("1. Alta profesor");
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

}
