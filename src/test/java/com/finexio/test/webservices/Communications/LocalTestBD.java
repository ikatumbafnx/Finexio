package com.finexio.test.webservices.Communications;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class LocalTestBD {

    public void testMangoDB() {

        // MongoClient client = MongoClients.create(
        // "mongodb+srv://pgcommunication:Y9hM894uupYK@cluster0.hyuqu.mongodb.net/admin?authSource=admin&replicaSet=atlas-4ye573-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        // MongoDatabase database = client.getDatabase("Communication");
        // MongoCollection<Document> token =
        // database.getCollection("EmailVerifications");

        // for (String name : database.listCollectionNames()) {

        // System.out.println("The Name of the Collection Is : " + name);
        // }

        // try (MongoClient mongoClient = MongoClients.create(
        // "mongodb+srv://pgcommunication:Y9hM894uupYK@cluster0.hyuqu.mongodb.net/admin?authSource=admin&replicaSet=atlas-4ye573-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"))
        // {
        // List<Document> databases = mongoClient.listDatabases().into(new
        // ArrayList<>());
        // databases.forEach(db -> System.out.println(db.toJson()));
        // }

        // Document yoyo = token.find(new Document("_id",
        // "61b2584a245b2395def32b6a")).first();

        // System.out.println(yoyo.toJson());

        // MongoClient client = MongoClients.create(
        // "mongodb+srv://pgcommunication:Y9hM894uupYK@cluster0.hyuqu.mongodb.net/admin?authSource=admin&replicaSet=atlas-4ye573-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        // MongoIterable<String> databases = client.listDatabaseNames();
        // for (String dbName : databases) {
        // System.out.println("- Database: " + dbName);

        // MongoDatabase db = client.getDatabase(dbName);

        // MongoIterable<String> collections = db.listCollectionNames();
        // for (String colName : collections) {
        // System.out.println("\t + Collection: " + colName);
        // }
        // }

    }

}
