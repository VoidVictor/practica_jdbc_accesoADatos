package com.ies.serpis.dao;

import com.ies.serpis.model.Actividad;
import com.ies.serpis.model.Resenya;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenyaDaoMongo implements Dao<Resenya,Integer>{

    private final MongoCollection<Document> coleccion = ConexionMongo.getDatabase().getCollection("resenyas");

    public ResenyaDaoMongo() {
        try {
            coleccion.createIndex(Indexes.ascending("id_excursion"), new IndexOptions().unique(true));
        } catch (MongoException e){
            throw new RuntimeException("No se ha podido crear el índice.", e);
        }
    }

    @Override
    public Integer crear(Resenya resenya) {
        try {
            coleccion.insertOne(convertirADocument(resenya));
            return resenya.getId_excursion();
        } catch (MongoException e){
            throw new RuntimeException("No se ha podido crear la reseña.", e);
        }
    }

    @Override
    public Optional<Resenya> encontrarPorID(Integer id) {
        try {
            Document doc = coleccion.find(Filters.eq("id_excursion", id)).first();
            if (doc == null) {
                return Optional.empty();
            }
            return Optional.of(convertirAResenya(doc));
        } catch (MongoException e) {
            throw new RuntimeException("No se ha podido buscar la reseña.", e);
        }
    }

    @Override
    public List<Resenya> encontrarTodos() {
        try {
            return convertirALista(coleccion.find().sort(Sorts.descending("fechaCreacion")));
        } catch (MongoException e){
            throw new RuntimeException("No se han podido buscar las reseñas.", e);
        }
    }

    @Override
    public boolean actualizar(Resenya resenya) {
        try {
            Document doc = convertirADocument(resenya);
            UpdateResult resultado = coleccion.replaceOne(Filters.eq("id_excursion", resenya.getId_excursion()), doc);
            return resultado.getMatchedCount() == 1;
        }catch (MongoException e){
            throw new RuntimeException("No se ha podido actualizar la reseña.", e);
        }
    }

    @Override
    public boolean borrar(Integer id) {
        try {
            DeleteResult resultado = coleccion.deleteOne(Filters.eq("id_excursion", id));
            return resultado.getDeletedCount() == 1;
        } catch (MongoException e){
            throw new RuntimeException("No se ha podido borrar la reseña.", e);
        }
    }

    public List<Resenya> buscarPorValoracionGlobal(int valoracionGlobal) {
        try {
            return convertirALista(coleccion.find(Filters.eq("valoracionGlobal", valoracionGlobal)));
        } catch (MongoException e){
            throw new RuntimeException("No se han podido buscar las reseñas por valoración.", e);
        }
    }

    public List<Resenya> buscarPorValoracionActividades(int valoracion) {
        try {
            return convertirALista(coleccion.find(Filters.lt("actividades.valoracion", valoracion)));
        } catch (MongoException e){
            throw new RuntimeException("No se han podido buscar las reseñas por valoración de actividades.", e);
        }
    }

    public List<Resenya> buscarConIncidencias() {
        try {
            return convertirALista(coleccion.find(Filters.exists("incidencias.0")));
        } catch (MongoException e){
            throw new RuntimeException("No se han podido buscar las reseñas con incidencias.", e);
        }
    }

    // ------ MÉTODOS AUXILIARES -------

    private List<Resenya> convertirALista(FindIterable<Document> documentos){
        List<Resenya> resenyas = new ArrayList<>();
        for (Document doc: documentos){
            Resenya resenya = convertirAResenya(doc);
            resenyas.add(resenya);
        }
        return resenyas;
    }

    private Document convertirADocument (Resenya resenya){
        List<Document> actividades = new ArrayList<>();
        for (Actividad actividad : resenya.getActividades()) {
            actividades.add(new Document("nombre", actividad.getNombre())
                    .append("valoracion", actividad.getValoracion()));
        }
        return new Document("id_excursion", resenya.getId_excursion())
                .append("fechaCreacion", resenya.getFechaCreacion())
                .append("valoracionGlobal", resenya.getValoracionGlobal())
                .append("comentario", resenya.getComentario())
                .append("actividades", actividades)
                .append("incidencias", resenya.getIncidencias());
    }

    private Resenya convertirAResenya(Document doc) {
        List<Actividad> actividades = new ArrayList<>();
        for (Document docActividad : doc.getList("actividades", Document.class)) {
            actividades.add(new Actividad(docActividad.getString("nombre"),
                    docActividad.getInteger("valoracion")));
        }

        LocalDate fechaCreacion = doc.getDate("fechaCreacion").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        return new Resenya(doc.getInteger("id_excursion"),
                fechaCreacion,
                doc.getInteger("valoracionGlobal"),
                doc.getString("comentario"),
                actividades,
                doc.getList("incidencias", String.class));
    }
}
