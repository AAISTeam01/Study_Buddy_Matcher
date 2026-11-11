
package com.studybuddy.services;

import com.studybuddy.helper.DatabaseConnection;
import com.studybuddy.schemas.UserSchema;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class StudentDAO {

    // Save complete student information
    public boolean saveStudent(UserSchema student) {

        try {
            MongoDatabase database =
                    DatabaseConnection.getDatabase();

            MongoCollection<Document> collection =
                    database.getCollection("Students");

            Document document = new Document();

            // Registration details
            document.append("userID", student.getUserID());
            document.append("name", student.getName());
            document.append("email", student.getEmail());
            document.append("course", student.getCourse());
            document.append("branch", student.getBranch());
            document.append("semester", student.getSemester());
            document.append("password", student.getPassword());

            // Profile details
            document.append("subjects", student.getSubjects());
            document.append("interest", student.getInterest());
            document.append("studyMode", student.getStudyMode());
            document.append("studyTime", student.getStudyTime());

            collection.insertOne(document);

            System.out.println("Student saved successfully!");

            return true;

        } catch (Exception e) {
            System.out.println("Error saving student data:");
            e.printStackTrace();
            return false;
        }
    }

    // Check whether User ID already exists
    public boolean isUserIDExists(String userID) {

        MongoDatabase database =
                DatabaseConnection.getDatabase();

        MongoCollection<Document> collection =
                database.getCollection("Students");

        return collection.find(
                new Document("userID", userID.trim().toUpperCase())
        ).first() != null;
    }

    // Check whether Email already exists
    public boolean isEmailExists(String email) {

        MongoDatabase database =
                DatabaseConnection.getDatabase();

        MongoCollection<Document> collection =
                database.getCollection("Students");

        return collection.find(
                new Document("email", email.trim().toLowerCase())
        ).first() != null;
    }

    // Find student during login
    public Document findUserForLogin(String email, String password) {

        MongoDatabase database = DatabaseConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("Students");

        Document query = new Document(
                "email", email.trim().toLowerCase()
        ).append("password", password);

        return collection.find(query).first();
    }
}
