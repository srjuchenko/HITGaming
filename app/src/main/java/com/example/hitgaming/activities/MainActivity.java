package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hitgaming.R;
import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.APIResult;
import com.example.hitgaming.models.GameResult;
import com.example.hitgaming.services.GameDataService;
import com.example.hitgaming.services.RetrofitClass;
import com.example.hitgaming.utils.Constants;
import com.example.hitgaming.utils.Credentials;

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
    // others
    private List<GameResult> gameResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiFields();
        showProgressCircle();
        getGamesFromAPI();
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
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResult> call, @NonNull Throwable t) {
                showError();
            }
        });
    }

    private void intiFields() {
        recyclerView = findViewById(R.id.games_items_recycle);
        progress = findViewById(R.id.progress);
        errorText = findViewById(R.id.txt_error);
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

    private void showError() {
        recyclerView.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
    }


    private void viewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this, gameResultList);
        recyclerView.setAdapter(gameAdapter);
    }

}