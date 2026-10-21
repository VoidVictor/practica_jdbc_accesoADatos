package com.ies.serpis.service;

import com.ies.serpis.dao.ProfesorDaoJdbc;
import com.ies.serpis.exceptions.AnioNacimientoInvalidoException;
import com.ies.serpis.exceptions.CampoObligatorioException;
import com.ies.serpis.exceptions.IdInvalidoException;
import com.ies.serpis.exceptions.TelefonoSinPrefijoException;
import com.ies.serpis.model.Profesor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProfesorService {

    private final ProfesorDaoJdbc dao = new ProfesorDaoJdbc();

    public Integer crearProfesor (String nombre, String apellidos, int anyo_nacimiento, String telefono){
        validarCampos(nombre, apellidos, anyo_nacimiento, telefono);
        return dao.crear(new Profesor(nombre, apellidos, anyo_nacimiento, telefono));
    }

    public List<Profesor> listarProfesores() {
        return dao.encontrarTodos();
    }

    public Optional<Profesor> obtenerProfesorPorID (Integer id){
        if (id <= 0) throw new IdInvalidoException("Los IDs empiezan a partir de 1");
        return dao.encontrarPorID(id);
    }

    // ---------- MÉTODOS AUXILIARES ----------

    private void validarCampos(String nombre, String apellidos, int anyo_nacimiento, String telefono) {
        if (nombre == null || nombre.isBlank())throw new CampoObligatorioException("Es obligatorio poner el nombre.");
        if (apellidos == null || apellidos.isBlank())throw new CampoObligatorioException("Es obligatorio poner los apellidos.");
        if (anyo_nacimiento < (LocalDate.now().getYear()-67) || anyo_nacimiento > (LocalDate.now().getYear()-18)) throw new AnioNacimientoInvalidoException("Error en el año de nacimiento. El profesor debe estar en edad legal para trabajar.");
        if (telefono == null || nombre.isBlank())throw new CampoObligatorioException("Es obligatorio poner el teléfono.");
        if (telefono.charAt(0) != '+') throw new TelefonoSinPrefijoException("El teléfono debe empezar con el prefijo internacional '+'. Por ejemplo +34 en España.");
    }


}
