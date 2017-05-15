/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

/**
 *
 * @author cristian
 */
public class Model {

    MongoClient mongoClient = new MongoClient();

    MongoDatabase database;

    MongoCollection<Document> col;

    public Model() {

    }

//    public FindIterable<Document> getAllDocuments(String colection) {
//
//        col = database.getCollection(colection);
//
//        return col.find();
//    }
    public MongoIterable getAllDatabases() {

        return mongoClient.listDatabaseNames();

    }

    public FindIterable<Document> getDocuments(String collection, String db) {

        return mongoClient.getDatabase(db).getCollection(collection).find();

    }

    public MongoIterable getCollections(String db) {

        return mongoClient.getDatabase(db).listCollectionNames();

    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getCol() {
        return col;
    }

    public void setCol(MongoCollection<Document> col) {
        this.col = col;
    }
    
    

}
