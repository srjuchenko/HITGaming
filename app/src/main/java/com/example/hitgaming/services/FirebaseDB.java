package com.example.hitgaming.services;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.hitgaming.activities.LoginActivity;
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
            return new FirebaseDB();
        } else {
            return firebaseDB;
        }
    }

    public void addUser(User user) {
        db.collection("usersDB")
                .document(user.getUserId())
                .set(user);

        System.out.println("the user: " + user + " was added successfully");

    }


}
