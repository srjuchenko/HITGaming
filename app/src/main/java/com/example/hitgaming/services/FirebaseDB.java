package com.example.hitgaming.services;


import com.example.hitgaming.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
    }

    public void getFavoriteGamesByUID(String uid
            , final OnSuccessListener<DocumentSnapshot> onSuccessListener
            , final OnFailureListener onFailureListener) {
        db.collection("usersDB")
                .document(uid)
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void addGameToFavorites(String uid, String gameID, final OnSuccessListener<Void> onSuccessListener, final OnFailureListener onFailureListener) {
        DocumentReference userRef = db.collection("usersDB").document(uid);

        // Use FieldValue.arrayUnion to add the gameId to the favorites array
        userRef.update("favoriteGames", FieldValue.arrayUnion(gameID))
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void removeGameFromFavorites(String uid, String gameID, final OnSuccessListener<Void> onSuccessListener, final OnFailureListener onFailureListener) {
        DocumentReference userRef = db.collection("usersDB").document(uid);

        // Use FieldValue.arrayRemove to remove the gameId from the favorites array
        userRef.update("favoriteGames", FieldValue.arrayRemove(gameID))
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }



}
