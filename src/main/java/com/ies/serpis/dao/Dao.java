package com.ies.serpis.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T,K> {
    K crear(T t);
    Optional<T> encontrarPorID(K id);
    List<T> encontrarTodos();
    boolean actualizar(T t);
    boolean borrar(K id);
}
