package com.ies.serpis.service;

import com.ies.serpis.dao.ExcursionDaoJdbc;
import com.ies.serpis.dao.GrupoDaoJdbc;
import com.ies.serpis.exceptions.*;
import com.ies.serpis.model.Excursion;
import com.ies.serpis.model.Grupo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ExcursionService {

    ExcursionDaoJdbc daoExcursion = new ExcursionDaoJdbc();
    GrupoDaoJdbc daoGrupo = new GrupoDaoJdbc();

    public Integer crearExcursion (int id_grupo, String destino, int duracion, String fecha, double importe){
        validarGrupo(id_grupo);
        validarFecha(fecha);
        validarCampos(destino, duracion, importe);
        String estado = "Pendiente";
        return daoExcursion.crear(new Excursion(id_grupo, destino, duracion, fecha, importe, estado));
    }

    public boolean borrarExcursion (Integer id){
        validarExcursion(id);
        return daoExcursion.borrar(id);
    }

    public List<Excursion> listarExcursiones(){
        return daoExcursion.encontrarTodos();
    }

    public Excursion obtenerExcursionPorID(Integer id) {
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1");
        return daoExcursion.encontrarPorID(id)
                .orElseThrow(() -> new IdInvalidoException("No existe ninguna excursión con ID " + id));
    }

    public boolean modificarExcursion (Integer id, String fecha){
        validarExcursion(id);
        validarFecha(fecha);
        return daoExcursion.actualizarFecha(id, fecha);
    }

    private void validarGrupo (Integer id_grupo){
        if (id_grupo <= 0) throw new IdInvalidoException("El ID debe ser positivo.");
        if (!daoGrupo.existeGrupo(id_grupo)) throw new GrupoInvalidoException("El grupo no existe en la base de datos.");
    }

    private void validarFecha (String fecha){
        if (fecha == null || fecha.isBlank())throw new CampoObligatorioException("Es obligatorio poner la fecha.");
        LocalDate fechaConvertida;
        try {
            DateTimeFormatter formatoFecha = Excursion.FORMATO_FECHA;
            fechaConvertida = LocalDate.parse(fecha, formatoFecha);
        } catch (DateTimeParseException e){
            throw new FechaInvalidaException("La fecha debe contener justo 10 caracteres. Formato dd/MM/yyyy.");
        }
        if (fechaConvertida.isBefore(LocalDate.now().plusDays(10))) throw new FechaInvalidaException("La excursión debe planearse como mínimo con 10 días de antelación.");
    }

    private void validarCampos (String destino, int duracion, double importe){
        if (destino == null || destino.isBlank())throw new CampoObligatorioException("Es obligatorio poner el destino.");
        if (duracion <= 0) throw new DuracionInvalidaException("La duración debe durar al menos una hora.");
        if (importe < 0) throw new CantidadInvalidaException("Las excursiones deben ser gratis (0 euros) o con un importe positivo.");
    }

    private void validarExcursion (Integer id){
        if (!daoExcursion.existeExcursion(id)) throw new ExcursionInvalidaException("La excursión no existe con el ID " + id);
        if (daoExcursion.estaFinalizada(id)) throw new ExcursionInvalidaException("No se puede borrar/modificar una que excursión que no esté finalizada.");
    }
}
