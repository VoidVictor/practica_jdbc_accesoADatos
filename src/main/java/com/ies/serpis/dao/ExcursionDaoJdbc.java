package com.ies.serpis.dao;

import com.ies.serpis.model.Excursion;
import com.ies.serpis.model.Grupo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExcursionDaoJdbc implements Dao<Excursion, Integer> {


    @Override
    public Integer crear(Excursion excursion) {
        String sql = "INSERT INTO excursion (id_grupo, destino, duracion, fecha, importe, estado) VALUES (?,?,?,?,?,?)";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            sentencia.setInt(1,excursion.getId_grupo());
            sentencia.setString(2,excursion.getDestino());
            sentencia.setInt(3, excursion.getDuracion());
            sentencia.setDate(4, Date.valueOf(LocalDate.parse(excursion.getFecha(), Excursion.FORMATO_FECHA)));
            sentencia.setDouble(5,excursion.getImporte());
            sentencia.setString(6, excursion.getEstado());

            int filas = sentencia.executeUpdate();

            if (filas != 1) {
                throw new RuntimeException("El INSERT no afectó exactamente a 1 fila (filas = " + filas + ")");
            }

            try (ResultSet keys = sentencia.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    excursion.setId_excursion(id);
                    return id;
                } else {
                    throw new RuntimeException("Asegúrate que el id es autoincrement. No hay clave generada.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creando la excursión.");
        }
    }

    @Override
    public Optional<Excursion> encontrarPorID(Integer id) {
        String sql = "SELECT * FROM excursion WHERE id_excursion = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);

            try (ResultSet rs = sentencia.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Excursion(rs.getInt("id_excursion"),
                            rs.getInt("id_grupo"),
                            rs.getString("destino"),
                            rs.getInt("duracion"),
                            rs.getDate("fecha").toLocalDate().format(Excursion.FORMATO_FECHA),
                            rs.getDouble("importe"),
                            rs.getString("estado")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al buscar la excursión.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Excursion> encontrarTodos() {
        List<Excursion> lista = new ArrayList<>();
        String sql = "SELECT * FROM excursion";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet rs = sentencia.executeQuery();
        ) {
            while (rs.next()) {
                lista.add(new Excursion(rs.getInt("id_excursion"),
                        rs.getInt("id_grupo"),
                        rs.getString("destino"),
                        rs.getInt("duracion"),
                        rs.getDate("fecha").toLocalDate().format(Excursion.FORMATO_FECHA),
                        rs.getDouble("importe"),
                        rs.getString("estado")));

            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al listar las excursiones.", e);
        }
        return lista;
    }

    public boolean actualizarFecha (Integer id, String fecha) {
        String sql = "UPDATE excursion SET fecha=? WHERE id_excursion=?";
        try (Connection conexion = Conexion.establecerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setDate(1, Date.valueOf(LocalDate.parse(fecha, Excursion.FORMATO_FECHA)));
            sentencia.setInt(2, id);

            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando la fecha de la excursión", e);
        }
    }

    @Override
    public boolean borrar(Integer id) {
        String sql = "DELETE FROM excursion WHERE id_excursion = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar la excursión.");
        }
    }

    public boolean estaFinalizada (Integer id){
        String sql = "SELECT estado FROM excursion WHERE id_excursion = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery();) {
                if (rs.next()){
                    String estado = rs.getString("estado");
                    return estado.equals("Finalizada");
                }
                throw new RuntimeException("No existe ninguna excursión el ID " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al comprobar si existe la excursión.", e);
        }
    }

    public boolean existeExcursion (Integer id){
        String sql = "SELECT * FROM excursion WHERE id_excursion = ?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la base de datos al comprobar si existe la excursión.", e);
        }
    }

    @Override
    public boolean actualizar(Excursion excursion) {
        throw new UnsupportedOperationException("No está permitido actualizar todos los datos de una excursión.");
    }
}
