package com.example.hitgaming.services;


import com.example.hitgaming.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDB {
    private static FirebaseDB firebaseDB = null;
    private FirebaseFirestore db;
    private FirebaseDB() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseDB getInstance() {
        if (firebaseDB == null) {
            firebaseDB = new FirebaseDB();
        }
        return firebaseDB;
    }

    public void addUser(User user) {
        db.collection("usersDB")
                .document(user.getUserId())
                .set(user);

        System.out.println("the user: " + user + " was added successfully");

    }


}
