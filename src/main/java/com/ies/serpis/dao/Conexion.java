package com.ies.serpis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String url = "jdbc:mysql://localhost:3306/gestion_excursiones";
    private static final String user = "root";
    private static final String pass = "serpis";

    public static Connection establecerConexion () throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

}
