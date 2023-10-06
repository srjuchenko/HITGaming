package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;


import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hitgaming.R;
import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.APIResult;
import com.example.hitgaming.models.GameResult;
import com.example.hitgaming.models.User;
import com.example.hitgaming.services.FirebaseDB;
import com.example.hitgaming.services.GameDataService;
import com.example.hitgaming.services.GameDataServiceByGenre;
import com.example.hitgaming.services.GameDataServiceByQuery;
import com.example.hitgaming.services.GameInfoServiceByID;
import com.example.hitgaming.services.RetrofitClass;
import com.example.hitgaming.utils.Constants;
import com.example.hitgaming.utils.Credentials;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // widgets
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private TextView errorText;
    private SearchView searchView;

    private Button actionGenreBtn
            , sportsGenreBtn
            , adventureGenreBtn
            , shooterGenreBtn
            , rpgGenreBtn
            , racingGenreBtn
            , favoritesBtn;


    // others
    private List<GameResult> gameResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiFields();
        showProgressCircle();
        setListeners();
        getGamesFromAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearSearchInput();
        searchView.clearFocus();
    }


    private void setListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressCircle();
                getGamesBySearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        favoritesBtn.setOnClickListener(v -> {
            showProgressCircle();
            gameResultList.clear();
            showFavorites();
        });

        listenGenreBtn(actionGenreBtn, Constants.GENRE_ACTION);
        listenGenreBtn(sportsGenreBtn, Constants.GENRE_SPORTS);
        listenGenreBtn(adventureGenreBtn, Constants.GENRE_ADVENTURE);
        listenGenreBtn(rpgGenreBtn, Constants.GENRE_RPG);
        listenGenreBtn(racingGenreBtn, Constants.GENRE_RACING);
        listenGenreBtn(shooterGenreBtn, Constants.GENRE_SHOOTER);


    }

    private void showFavorites() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserUID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        FirebaseDB db = FirebaseDB.getInstance();

        db.getFavoriteGamesByUID(currentUserUID,
                documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // User document exists
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            ArrayList<String> favoriteGames = user.getFavoriteGames();
                            if (favoriteGames.size() == 0) {
                                showError("you don't have favorite games yet");
                            } else {
                                for (String id: favoriteGames) {
                                    getGameById(id);
                                }

                            }
                        }
                    }
                },
                e -> showToast(Constants.GET_FAVORITES_ERROR));
    }

    private void getGameById(String gameID) {
        GameInfoServiceByID gameInfoServiceByID = RetrofitClass.getGameInfo();
        Call<GameResult> call = gameInfoServiceByID.getInfo(gameID, Credentials.API_KEY);

        call.enqueue(new Callback<GameResult>() {
            @Override
            public void onResponse(@NonNull Call<GameResult> call, @NonNull Response<GameResult> response) {
                GameResult gameResult = response.body();

                if (gameResult != null) {
                    gameResultList.add(gameResult);
                    viewData();
                }
                else {
                    showError(Constants.API_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GameResult> call, @NonNull Throwable t) {
                showError(Constants.API_ERROR);
            }
        });
    }


    private void listenGenreBtn(Button actionGenreBtn, String genre) {
        actionGenreBtn.setOnClickListener(v -> {
            showProgressCircle();
            getGamesByGenre(genre);
        });
    }

    private void getGamesByGenre(String genre) {
        GameDataServiceByGenre gameDataServiceByGenre = RetrofitClass.getGenreService();
        Call<APIResult> call = gameDataServiceByGenre.getResults(Credentials.API_KEY,Constants.PAGE_SIZE,genre);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(@NonNull Call<APIResult> call, @NonNull Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = result.getResults();
                    if (gameResultList.size() == 0) {
                        showError(Constants.GENRE_ERROR);
                    } else {
                        viewData();
                        hideProgressCircle();
                    }

                } else {
                    showError(Constants.GENRE_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResult> call, @NonNull Throwable t) {
                showError(Constants.API_ERROR);
            }
        });

    }

    private void getGamesBySearchQuery(String query) {
        GameDataServiceByQuery gameDataServiceByQuery = RetrofitClass.getSearchService();
        Call<APIResult> call = gameDataServiceByQuery.getResults(Credentials.API_KEY,Constants.PAGE_SIZE,query);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(@NonNull Call<APIResult> call, @NonNull Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = result.getResults();
                    if (gameResultList.size() == 0) {
                        showError(Constants.SEARCH_ERROR);
                        clearSearchInput();
                    } else {
                        viewData();
                        clearSearchInput();
                        hideProgressCircle();
                    }

                } else {
                    showError(Constants.SEARCH_ERROR);
                    clearSearchInput();
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResult> call, @NonNull Throwable t) {
                showError(Constants.API_ERROR);
            }
        });
    }

    public void getGamesFromAPI() {
        GameDataService gameDataService = RetrofitClass.getService();
        Call<APIResult> call = gameDataService.getResults(Credentials.API_KEY, Constants.PAGE_SIZE);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(@NonNull Call<APIResult> call, @NonNull Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = result.getResults();
                    viewData();
                    hideProgressCircle();
                } else {
                    showError(Constants.API_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResult> call, @NonNull Throwable t) {
                showError(Constants.API_ERROR);
            }
        });
    }

    private void intiFields() {
        recyclerView = findViewById(R.id.games_items_recycle);
        progress = findViewById(R.id.progress);
        errorText = findViewById(R.id.txt_error);
        searchView = findViewById(R.id.input_search);
        actionGenreBtn = findViewById(R.id.btn_action);
        sportsGenreBtn = findViewById(R.id.btn_sport);
        racingGenreBtn = findViewById(R.id.btn_racing);
        shooterGenreBtn = findViewById(R.id.btn_shooter);
        rpgGenreBtn = findViewById(R.id.btn_rpg);
        adventureGenreBtn = findViewById(R.id.btn_adventure);
        favoritesBtn = findViewById(R.id.btn_show_favorites);
    }

    private void showProgressCircle() {
        recyclerView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgressCircle() {
        recyclerView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }

    private void showError(String msg) {
        recyclerView.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(msg);
    }

    private void clearSearchInput() {
        searchView.setQuery("",false);

    }

    private void viewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GameAdapter gameAdapter = new GameAdapter(this, gameResultList);
        recyclerView.setAdapter(gameAdapter);
        hideProgressCircle();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}