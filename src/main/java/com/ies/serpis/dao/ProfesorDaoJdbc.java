package com.ies.serpis.dao;

import com.ies.serpis.model.Profesor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorDaoJdbc implements Dao<Profesor, Integer>{

    @Override
    public Integer crear(Profesor profesor) {
        String sql = "INSERT INTO profesor (nombre, apellidos, anyo_nacimiento, telefono) VALUES (?,?,?,?)";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            sentencia.setString(1, profesor.getNombre());
            sentencia.setString(2, profesor.getApellidos());
            sentencia.setInt(3, profesor.getAnyoNacimiento());
            sentencia.setString(4, profesor.getTelefono());

            int filas = sentencia.executeUpdate();

            if (filas != 1) {
                throw new RuntimeException("El INSERT no afectó exactamente a 1 fila (filas = " + filas + ")");
            }

            try (ResultSet keys = sentencia.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    profesor.setId_profesor(id);
                    return id;
                } else {
                    throw new RuntimeException("Asegúrate que el id es autoincrement. No hay clave generada.");
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error creando el profesor.");

        }
    }

    @Override
    public Optional<Profesor> encontrarPorID(Integer id) {

        String sql = "SELECT * FROM profesor WHERE id_profesor = ?";
        try(
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ) {
            sentencia.setInt(1, id);

            try(ResultSet rs = sentencia.executeQuery();){
                if (rs.next()){
                    return Optional.of(new Profesor(rs.getInt("id_profesor"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getInt("anyo_nacimiento"),
                            rs.getString("telefono")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al buscar el profesor.", e);
        }
        return Optional.empty();

    }

    @Override
    public List<Profesor> encontrarTodos() {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT * FROM profesor";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet rs = sentencia.executeQuery();
                ){
            while (rs.next()){
                lista.add(new Profesor(rs.getInt("id_profesor"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getInt("anyo_nacimiento"),
                        rs.getString("telefono")));

            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al listar los profesores.", e);
        }
        return lista;

    }

    public List<Profesor> encontrarProfesoresPorExcursion(Integer id) {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM profesor p " +
                "JOIN prof_excu pe ON pe.id_profesor = p.id_profesor " +
                "WHERE pe.id_excursion = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Profesor(rs.getInt("id_profesor"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getInt("anyo_nacimiento"),
                            rs.getString("telefono")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al buscar los profesores de la excursión.", e);
        }
        return lista;
    }

    //Estos dos métodos de abajo no se van a utilizar

    @Override
    public boolean actualizar(Profesor profesor) {
        throw new UnsupportedOperationException("No está permitido actualizar un profesor");
    }

    @Override
    public boolean borrar(Integer id) {
        throw new UnsupportedOperationException("No está permitido borrar un profesor");
    }
}
