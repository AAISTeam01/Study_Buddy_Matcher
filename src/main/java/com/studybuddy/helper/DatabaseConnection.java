package com.studybuddy.helper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {

        if (mongoClient == null) {

            String uri = System.getenv("STUDY_BUDDY_MONGO_URI");

            if (uri == null || uri.trim().isEmpty()) {
                throw new IllegalStateException(
                    "MONGODB_URI environment variable is missing."
                );
            }

            mongoClient = MongoClients.create(uri);
        }

        return mongoClient.getDatabase("StudyBuddy");
    }
}