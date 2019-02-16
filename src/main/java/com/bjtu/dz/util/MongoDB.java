package com.bjtu.dz.util;

import com.bjtu.dz.bean.JSONClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class MongoDB {
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public MongoDB() {
        try {
            // 连接到 mongodb 服务
            mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("nosql");
            System.out.println("Connect to database successfully");
            try {
                collection = mongoDatabase.getCollection("movie");
                if (collection == null) throw new Exception();
            } catch (Exception e) {
                mongoDatabase.createCollection("movie");
                collection = mongoDatabase.getCollection("movie");
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public void insertOne(JSONClass jClass){
        Document document = new Document("username", jClass.getName()).
                append("gender", jClass.getGender()).
                append("age", jClass.getAge()).
                append("occupation", jClass.getOccupation()).
                append("movieId", jClass.getMovie().getId()).
                append("movieTitle", jClass.getMovie().getTitle()).
                append("rating", jClass.getMovie().getRating());
//        List<Document> documents = new ArrayList<Document>();
//        documents.add(document);
        collection.insertOne(document);
//        collection.insertMany(documents);
    }
}
