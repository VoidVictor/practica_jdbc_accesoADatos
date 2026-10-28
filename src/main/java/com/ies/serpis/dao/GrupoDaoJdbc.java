package com.ies.serpis.dao;

import com.ies.serpis.model.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrupoDaoJdbc implements Dao <Grupo, Integer> {
    @Override
    public Integer crear(Grupo grupo) {
        String sql = "INSERT INTO grupo(curso, etapa_educativa, cantidad_alumnos) VALUES (?,?,?)";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            sentencia.setString(1, grupo.getCurso());
            sentencia.setString(2, grupo.getEtapaEducativa());
            sentencia.setInt(3, grupo.getCantidadAlumnos());

            int filas = sentencia.executeUpdate();

            if (filas != 1) {
                throw new RuntimeException("El INSERT no afectó exactamente a 1 fila (filas = " + filas + ")");
            }

            try (ResultSet keys = sentencia.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    grupo.setId_grupo(id);
                    return id;
                } else {
                    throw new RuntimeException("Asegúrate que el id es autoincrement. No hay clave generada.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido crear el grupo.", e);

        }
    }

    @Override
    public Optional<Grupo> encontrarPorID(Integer id) {
        String sql = "SELECT * FROM grupo WHERE id_grupo = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);

            try (ResultSet rs = sentencia.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Grupo(rs.getInt("id_grupo"),
                            rs.getString("curso"),
                            rs.getString("etapa_educativa"),
                            rs.getInt("cantidad_alumnos")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido buscar el grupo.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Grupo> encontrarTodos() {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupo";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet rs = sentencia.executeQuery();
        ) {
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("id_grupo"),
                        rs.getString("curso"),
                        rs.getString("etapa_educativa"),
                        rs.getInt("cantidad_alumnos")));

            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido listar los grupos.", e);
        }
        return lista;
    }

    @Override
    public boolean borrar(Integer id) {
        String sql = "DELETE FROM grupo WHERE id_grupo = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido borrar el grupo.", e);
        }
    }

    // MÉTODOS AUXILIARES

    public boolean existeCurso (String curso){
        String sql = "SELECT * FROM grupo WHERE curso = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ) {
            sentencia.setString(1, curso);
            try (ResultSet rs = sentencia.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido comprobar el curso.", e);
        }
    }

    public boolean existeGrupo (Integer id){
        String sql = "SELECT * FROM grupo WHERE id_grupo = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido comprobar si existe el grupo.", e);
        }
    }

    //Este metodo de abajo no se va a utilizar

    @Override
    public boolean actualizar(Grupo grupo) {
        throw new UnsupportedOperationException("No está permitido actualizar un grupo");
    }
}
