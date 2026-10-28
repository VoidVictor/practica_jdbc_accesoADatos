package com.ies.serpis.service;

import com.ies.serpis.dao.ExcursionDaoJdbc;
import com.ies.serpis.dao.GrupoDaoJdbc;
import com.ies.serpis.dao.ProfesorDaoJdbc;
import com.ies.serpis.exceptions.*;
import com.ies.serpis.model.Excursion;
import com.ies.serpis.model.Profesor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcursionService {

    private final ExcursionDaoJdbc daoExcursion = new ExcursionDaoJdbc();
    private final GrupoDaoJdbc daoGrupo = new GrupoDaoJdbc();
    private final ProfesorDaoJdbc daoProfesor = new ProfesorDaoJdbc();

    public Integer crearExcursion (int id_grupo, String destino, int duracion, String fecha, double importe, List<Integer> IDsProfesoresAcompanyantes){
        validarGrupo(id_grupo);
        validarFecha(fecha);
        validarCampos(destino, duracion, importe);
        List<Profesor> profesoresAcompanyantes = validarProfesoresAcompanyantes(IDsProfesoresAcompanyantes);
        String estado = "Pendiente";
        Excursion excursion = new Excursion(id_grupo, destino, duracion, fecha, importe, estado);
        excursion.setProfesores(profesoresAcompanyantes);
        return daoExcursion.crear(excursion);
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

    public boolean finalizarExcursion (Integer id){
        validarExcursion(id);
        validarExcursionFinalizada(id);
        return daoExcursion.finalizarExcursion(id);
    }

    public List<Excursion> listarExcursionesConProfesores() {
        List<Excursion> excursiones = daoExcursion.encontrarTodos();
        for (Excursion excursion : excursiones) {
            excursion.setProfesores(daoProfesor.encontrarProfesoresPorExcursion(excursion.getId_excursion()));
        }
        return excursiones;
    }

    public List<Excursion> listarFinalizadasEntreFechas(String desdeFecha, String hastaFecha) {
        LocalDate fechaDesde = convertirFecha(desdeFecha);
        LocalDate fechaHasta = convertirFecha(hastaFecha);
        if (fechaDesde.isAfter(fechaHasta)) throw new FechaInvalidaException("La fecha inicial no puede ser posterior a la fecha final.");
        return daoExcursion.encontrarFinalizadasEntreFechas(desdeFecha, hastaFecha);
    }

    // ------ VALIDACIONES ------

    private void validarGrupo (Integer id_grupo){
        if (id_grupo <= 0) throw new IdInvalidoException("El ID debe ser positivo.");
        if (!daoGrupo.existeGrupo(id_grupo)) throw new GrupoInvalidoException("El grupo no existe en la base de datos.");
    }

    private LocalDate convertirFecha(String fecha) {
        if (fecha == null || fecha.isBlank()) throw new CampoObligatorioException("Es obligatorio poner la fecha.");
        try {
            return LocalDate.parse(fecha, Excursion.FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new FechaInvalidaException("Formato de fecha incorrecto o fecha inexistente. Usa dd/MM/yyyy.");
        }
    }

    private void validarFecha(String fecha) {
        LocalDate fechaConvertida = convertirFecha(fecha);
        if (fechaConvertida.isBefore(LocalDate.now().plusDays(10)))
            throw new FechaInvalidaException("La excursión debe planearse como mínimo con 10 días de antelación.");
    }

    private void validarCampos (String destino, int duracion, double importe){
        if (destino == null || destino.isBlank())throw new CampoObligatorioException("Es obligatorio poner el destino.");
        if (duracion <= 0) throw new DuracionInvalidaException("La duración debe durar al menos una hora.");
        if (importe < 0) throw new CantidadInvalidaException("Las excursiones deben ser gratis (0 euros) o con un importe positivo.");
    }

    private void validarExcursion (Integer id){
        if (!daoExcursion.existeExcursion(id)) throw new ExcursionInvalidaException("La excursión no existe con el ID " + id);
        if (daoExcursion.estaFinalizada(id)) throw new ExcursionInvalidaException("La excursión ya está finalizada y no admite cambios.");
    }

    private void validarExcursionFinalizada (Integer id){
        Excursion excursion = daoExcursion.encontrarPorID(id).orElseThrow(() -> new ExcursionInvalidaException("La excursión no existe con el ID " + id));
        LocalDate fechaConvertida = convertirFecha(excursion.getFecha());
        if (!fechaConvertida.isBefore(LocalDate.now())) throw new FechaInvalidaException("No se puede finalizar una excursión futura.");
    }

    private List<Profesor> validarProfesoresAcompanyantes(List<Integer> IDsProfesoresAcompanyantes) {
        if (IDsProfesoresAcompanyantes.isEmpty()) throw new ExcursionInvalidaException("Debe haber como mínimo un profesor en la excursión.");
        Set<Integer> comprobarDuplicados = new HashSet<>(IDsProfesoresAcompanyantes);
        if (IDsProfesoresAcompanyantes.size() != comprobarDuplicados.size()) throw new ExcursionInvalidaException("No está permitido incluir un profesor dos o más veces.");
        List<Profesor> profesores = new ArrayList<>();
        for (Integer id : IDsProfesoresAcompanyantes){
            Profesor profesor = daoProfesor.encontrarPorID(id)
                    .orElseThrow(() -> new IdInvalidoException("No existe ningún profesor con ID " + id));
            profesores.add(profesor);
        }
        return profesores;
    }
}
