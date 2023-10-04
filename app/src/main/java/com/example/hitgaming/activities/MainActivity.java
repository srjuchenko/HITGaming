package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hitgaming.R;
import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.APIResult;
import com.example.hitgaming.models.GameResult;
import com.example.hitgaming.services.GameDataService;
import com.example.hitgaming.services.GameDataServiceByQuery;
import com.example.hitgaming.services.RetrofitClass;
import com.example.hitgaming.utils.Constants;
import com.example.hitgaming.utils.Credentials;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // widgets
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private TextView errorText;
    private SearchView searchView;
    private ImageView hitLogo;

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
    }

    private void getGamesBySearchQuery(String query) {
        GameDataServiceByQuery gameDataServiceByQuery = RetrofitClass.getSearchService();
        Call<APIResult> call = gameDataServiceByQuery.getResults(Credentials.API_KEY,Constants.PAGE_SIZE,query);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = (ArrayList<GameResult>) result.getResults();
                    if (gameResultList.size() == 0) {
                        showError(Constants.SEARCH_ERROR);
                    } else {
                        viewData();
                        clearSearchInput();
                        hideProgressCircle();
                    }

                } else {
                    showError(Constants.SEARCH_ERROR);
                }
            }

            @Override
            public void onFailure(Call<APIResult> call, Throwable t) {
                showError(Constants.API_ERROR);
            }
        });
    }

    private void clearSearchInput() {
        searchView.setQuery("",false);
        hitLogo.requestFocus();
    }

    public void getGamesFromAPI() {
        GameDataService gameDataService = RetrofitClass.getService();
        Call<APIResult> call = gameDataService.getResults(Credentials.API_KEY, Constants.PAGE_SIZE);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(@NonNull Call<APIResult> call, @NonNull Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = (ArrayList<GameResult>) result.getResults();
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
        hitLogo = findViewById(R.id.icon_hit);
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


    private void viewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this, gameResultList);
        recyclerView.setAdapter(gameAdapter);
    }

}