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
            sentencia.setInt(3, profesor.getAnyo_nacimiento());
            sentencia.setString(4, profesor.getTelefono());

            int filas = sentencia.executeUpdate();

            if (filas != 1) { //Tengo que modificar esto y crear una excepcion personalizada
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
            throw new RuntimeException("Error creando el profesor. Error provocado: " + e);

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

        } catch (SQLException e){
            throw new RuntimeException("Error buscando el profesor. ID incorrecta.");
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
        } catch (SQLException e){
            throw new RuntimeException("No hay datos sobre profesores en la base de datos.");
        }return lista;

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
