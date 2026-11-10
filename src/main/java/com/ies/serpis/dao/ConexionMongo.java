package com.ies.serpis.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NOMBRE = "gestion_excursiones";

    private static MongoClient client;

    private ConexionMongo() {}

    public static MongoDatabase getDatabase() {
        if (client == null) {
            client = MongoClients.create(URI);
        }
        return client.getDatabase(DB_NOMBRE);
    }

    public static void close() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

}
