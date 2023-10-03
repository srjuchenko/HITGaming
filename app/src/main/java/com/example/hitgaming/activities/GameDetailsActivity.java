package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hitgaming.R;
import com.example.hitgaming.models.User;
import com.example.hitgaming.services.FirebaseDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class GameDetailsActivity extends AppCompatActivity {

    private TextView name;

    private TextView release;
    private ImageView img;
    private RatingBar rate;
    private Button shareBtn, favoritesBtn;
    private FirebaseDB db = FirebaseDB.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUserUID = mAuth.getCurrentUser().getUid();
    private ArrayList<String> favoriteGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        initFields();
        updateUI();
        setListeners();

    }



    private void setListeners() {
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGame();
            }
        });

        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteGames.contains(name.getText().toString())) {
                    removeGameFromFavorites();
                } else {
                   addGameToFavorites();
                   favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
                }
            }
        });
    }

    private void removeGameFromFavorites() {
        db.removeGameFromFavorites(currentUserUID, name.getText().toString(),
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Game removed from favorites successfully
                        showToast("the game was removed from the favorites");
                        favoriteGames.remove(name.getText().toString());
                        favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        showToast("can't remove the game from favorites");
                    }
                }
        );
    }

    private void addGameToFavorites() {
        db.addGameToFavorites(currentUserUID, name.getText().toString(),
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Game added to favorites successfully
                        showToast("the game was added to favorites");
                        favoriteGames.add(name.getText().toString());
                        favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        showToast("can't add the game to favorites");
                    }
                }
        );
    }


    private void shareGame() {
        String textToSend = "Hi you have to see the game: " + name.getText() ;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void initFields() {
        name = findViewById(R.id.txt_game_name);
        img = findViewById(R.id.img_big);
        rate = findViewById(R.id.rating);
        release = findViewById(R.id.txt_release_date);
        shareBtn = findViewById(R.id.btn_share);
        favoritesBtn = findViewById(R.id.btn_favorites);
        updateUserFavorites();
    }

    private void updateUI() {
        Intent intent = getIntent();
        if (intent != null) {
            String gameName = intent.getStringExtra("gameName");


            String gameImage = intent.getStringExtra("gameImg");

            String gameRating = intent.getStringExtra("gameRating");
            String releaseDate = intent.getStringExtra("gameRelease");

            name.setText(gameName);
            Glide.with(this)
                    .load(gameImage)
                    .into(img);
            rate.setRating(Float.parseFloat(gameRating));
            release.setText(release.getText() + " " + releaseDate);
        }
    }

    private void updateUserFavorites() {
        db.getFavoriteGamesByUID(currentUserUID,
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // User document exists
                            User user = documentSnapshot.toObject(User.class);
                            if (user != null) {
                                favoriteGames = user.getFavoriteGames();

                                if (favoriteGames.contains(name.getText().toString())) {
                                    // Game is in favorites, change button background color
                                    favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
                                } else {
                                    // Game is not in favorites, change button background color
                                   favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com);
                                }
                            }
                        } else {
                            // User document does not exist
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                    }
                });
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}