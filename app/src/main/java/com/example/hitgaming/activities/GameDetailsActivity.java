package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.hitgaming.R;
import com.example.hitgaming.models.APIResult;
import com.example.hitgaming.models.GameResult;
import com.example.hitgaming.models.User;
import com.example.hitgaming.services.FirebaseDB;
import com.example.hitgaming.services.GameInfoServiceByID;
import com.example.hitgaming.services.GameMovieServiceByID;
import com.example.hitgaming.services.RetrofitClass;
import com.example.hitgaming.utils.Constants;
import com.example.hitgaming.utils.Credentials;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailsActivity extends AppCompatActivity {
    // widgets
    private ProgressBar progressBar;
    private VideoView videoView;
    private TextView release,name, videoError, description;
    private ImageView img;
    private RatingBar rate;
    private Button shareBtn, favoritesBtn;
    // firebase
    private final FirebaseDB  db = FirebaseDB.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    // others
    String currentUserUID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    private ArrayList<String> favoriteGames;
    ArrayList<GameResult> results = new ArrayList<>();
    private String gameId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        initFields();
        updateUI();
        setListeners();
        getGameDescription();
        getVideoUrl();
    }

    private void getGameDescription() {
        GameInfoServiceByID gameInfoServiceByID = RetrofitClass.getGameInfo();
        Call<GameResult> call = gameInfoServiceByID.getInfo(gameId, Credentials.API_KEY);
        call.enqueue(new Callback<GameResult>() {
            @Override
            public void onResponse(@NonNull Call<GameResult> call, @NonNull Response<GameResult> response) {
                GameResult gameResult = response.body();

                if (gameResult != null) {
                    description.setText(gameResult.getDescriptionRaw());
                } else {
                    description.setText(Constants.DESCRIPTION_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GameResult> call, @NonNull Throwable t) {
                description.setText(Constants.DESCRIPTION_ERROR);
            }
        });
    }

    private void showVideo(String url) {
        if (url == null) return;
        videoView.setVisibility(View.VISIBLE);
        videoError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        Uri videoUrl = Uri.parse(url);
        videoView.setVideoURI(videoUrl);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }

    private void getVideoUrl() {
        GameMovieServiceByID gameMovieServiceByID = RetrofitClass.getMovieService();
        Call<APIResult> call = gameMovieServiceByID.getResults(gameId,Credentials.API_KEY);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(@NonNull Call<APIResult> call, @NonNull Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    results = (ArrayList<GameResult>) result.getResults();
                    if (results.size() == 0) {
                        showError();
                    } else {
                        showVideo(results.get(0).getData().get480());
                    }
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResult> call, @NonNull Throwable t) {
                showError();
            }
        });
    }

    private void removeGameFromFavorites() {
        db.removeGameFromFavorites(currentUserUID, gameId,
                aVoid -> {
                    showToast(Constants.REMOVED_FROM_FAVORITES);
                    favoriteGames.remove(gameId);
                    favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com);
                },
                e -> showToast(Constants.REMOVED_FROM_FAVORITES_ERROR)
        );
    }

    private void addGameToFavorites() {
        db.addGameToFavorites(currentUserUID, gameId,
                aVoid -> {
                    showToast(Constants.ADD_TO_FAVORITES);
                    favoriteGames.add(gameId);
                    favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
                },
                e -> showToast(Constants.ADD_TO_FAVORITES_ERROR)
        );
    }


    private void shareGame() {
        String textToSend = Constants.TEXT_TO_SHARE + name.getText();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void updateUI() {
        showProgress();
        Intent intent = getIntent();
        if (intent != null) {
            String gameName = intent.getStringExtra("gameName");
            String gameImage = intent.getStringExtra("gameImg");
            String gameRating = intent.getStringExtra("gameRating");
            String releaseDate = intent.getStringExtra("gameRelease");
            releaseDate = release.getText() + " " + releaseDate;
            gameId = intent.getStringExtra("gameId");

            name.setText(gameName);
            Glide.with(this)
                    .load(gameImage)
                    .into(img);
            rate.setRating(Float.parseFloat(gameRating));
            release.setText(releaseDate);
        }
    }

    private void updateUserFavorites() {
        db.getFavoriteGamesByUID(currentUserUID,
                documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // User document exists
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            favoriteGames = user.getFavoriteGames();

                            if (favoriteGames.contains(gameId)) {
                                favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
                            } else {
                               favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com);
                            }
                        }
                    }
                },
                e -> showToast(Constants.GET_FAVORITES_ERROR));
    }

    private void initFields() {
        name = findViewById(R.id.txt_game_name);
        img = findViewById(R.id.img_big);
        rate = findViewById(R.id.rating);
        release = findViewById(R.id.txt_release_date);
        shareBtn = findViewById(R.id.btn_share);
        favoritesBtn = findViewById(R.id.btn_favorites);
        videoView = findViewById(R.id.video);
        videoError = findViewById(R.id.video_error);
        progressBar = findViewById(R.id.video_progress);
        description = findViewById(R.id.txt_descr);
        updateUserFavorites();
    }

    private void setListeners() {
        shareBtn.setOnClickListener(v -> shareGame());

        favoritesBtn.setOnClickListener(v -> {
            if (favoriteGames.contains(gameId)) {
                removeGameFromFavorites();
            } else {
                addGameToFavorites();
                favoritesBtn.setBackgroundResource(R.drawable.favorites_star_svgrepo_com__1_);
            }
        });
    }

    private void showProgress() {
        videoView.setVisibility(View.GONE);
        videoError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showError() {
        videoView.setVisibility(View.GONE);
        videoError.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}