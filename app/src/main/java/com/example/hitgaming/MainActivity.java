package com.example.hitgaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.APIResult;
import com.example.hitgaming.models.Game;
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
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gameList = new ArrayList<>();
    private List<GameResult> gameResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.games_items_recycle);
        progress = findViewById(R.id.progress);

        recyclerView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        getGames();
    }

    public Object getGames() {
        GameDataService gameDataService = RetrofitClass.getService();
        Call<APIResult> call = gameDataService.getResults(Credentials.API_KEY, Credentials.FORMAT);

        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                APIResult result = response.body();
                if (result != null && result.getResults() != null) {
                    gameResultList = (ArrayList<GameResult>) result.getResults();
                    viewData();


                    recyclerView.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<APIResult> call, Throwable t) {

            }
        });

        return gameResultList;
    }

    private void viewData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this, gameResultList);
        recyclerView.setAdapter(gameAdapter);
    }

}