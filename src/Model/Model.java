/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author cristian
 */
public class Model {

    MongoClient mongoClient = new MongoClient();

    MongoDatabase database = mongoClient.getDatabase("consultes");

    public Model() {

        llistarDBs();
    }

    public void llistarDBs() {

        for (String s : database.listCollectionNames()) {
            System.out.println(s);            
        }

    }

}
