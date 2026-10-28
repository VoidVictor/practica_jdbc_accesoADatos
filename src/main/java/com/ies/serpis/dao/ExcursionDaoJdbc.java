package com.ies.serpis.dao;

import com.ies.serpis.model.Excursion;
import com.ies.serpis.model.Profesor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExcursionDaoJdbc implements Dao<Excursion, Integer> {


    @Override
    public Integer crear(Excursion excursion) {
        String sqlExcursion = "INSERT INTO excursion (id_grupo, destino, duracion, fecha, importe, estado) VALUES (?,?,?,?,?,?)";
        String sqlProfesor  = "INSERT INTO prof_excu (id_profesor, id_excursion) VALUES (?,?)";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentenciaExcursion = conexion.prepareStatement(sqlExcursion, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement sentenciaProfesor = conexion.prepareStatement(sqlProfesor);
                ) {
            sentenciaExcursion.setInt(1,excursion.getId_grupo());
            sentenciaExcursion.setString(2,excursion.getDestino());
            sentenciaExcursion.setInt(3, excursion.getDuracion());
            sentenciaExcursion.setDate(4, Date.valueOf(LocalDate.parse(excursion.getFecha(), Excursion.FORMATO_FECHA)));
            sentenciaExcursion.setDouble(5,excursion.getImporte());
            sentenciaExcursion.setString(6, excursion.getEstado());

            int filas = sentenciaExcursion.executeUpdate();

            if (filas != 1) {
                throw new RuntimeException("El INSERT no afectó exactamente a 1 fila (filas = " + filas + ")");
            }

            int id_excursion;
            try (ResultSet keys = sentenciaExcursion.getGeneratedKeys()) {
                if (keys.next()) {
                    id_excursion = keys.getInt(1);
                    excursion.setId_excursion(id_excursion);
                } else {
                    throw new RuntimeException("Asegúrate que el id es autoincrement. No hay clave generada.");
                }
            }

            for (Profesor profesor : excursion.getProfesores()) {
                sentenciaProfesor.setInt(1, profesor.getId_profesor());
                sentenciaProfesor.setInt(2, id_excursion);
                sentenciaProfesor.executeUpdate();
            }

            excursion.setId_excursion(id_excursion);
            return id_excursion;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido crear la excursión.", e);
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
            throw new RuntimeException("No se ha podido buscar la excursión.", e);
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
            throw new RuntimeException("No se ha podido listar las excursiones.", e);
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
            throw new RuntimeException("No se ha podido actualizar la fecha de la excursión", e);
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
            throw new RuntimeException("No se ha podido borrar la excursión.", e);
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
            throw new RuntimeException("No se ha podido comprobar si existe la excursión.", e);
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
            throw new RuntimeException("No se ha podido comprobar si existe la excursión.", e);
        }
    }

    public boolean existenExcursionesDeGrupo(Integer id) {
        String sql = "SELECT * FROM excursion WHERE id_grupo = ? LIMIT 1";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido comprobar si existe la excursión.", e);
        }
    }

    public boolean finalizarExcursion (Integer id){
        String sql = "UPDATE excursion SET estado=? WHERE id_excursion=?";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ){
            sentencia.setString(1, "Finalizada");
            sentencia.setInt(2, id);

            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("No ha sido posible finalizar la excursión.", e);
        }
    }

    public List<Excursion> encontrarFinalizadasEntreFechas(String desdeFecha, String hastaFecha) {
        List<Excursion> lista = new ArrayList<>();
        String sql = "SELECT * FROM excursion " +
                "WHERE estado = 'Finalizada' AND fecha BETWEEN ? AND ? " +
                "ORDER BY fecha";
        try (
                Connection conexion = Conexion.establecerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
        ) {
            sentencia.setDate(1, Date.valueOf(LocalDate.parse(desdeFecha, Excursion.FORMATO_FECHA)));
            sentencia.setDate(2, Date.valueOf(LocalDate.parse(hastaFecha, Excursion.FORMATO_FECHA)));
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Excursion(rs.getInt("id_excursion"),
                            rs.getInt("id_grupo"),
                            rs.getString("destino"),
                            rs.getInt("duracion"),
                            rs.getDate("fecha").toLocalDate().format(Excursion.FORMATO_FECHA),
                            rs.getDouble("importe"),
                            rs.getString("estado")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido listar las excursiones finalizadas.", e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Excursion excursion) {
        throw new UnsupportedOperationException("No está permitido actualizar todos los datos de una excursión.");
    }
}
