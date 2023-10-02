package com.example.hitgaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.Game;
import com.example.hitgaming.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button showAllBtn;

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // creating a dummy data
        // TODO get data from the api and store it
        Game game1 = new Game("game" ,"2012", Constants.IMG_URL, 4.2f);
        for (int i=0; i<20; i++) {
            gameList.add(game1);
        }

        recyclerView = findViewById(R.id.games_items_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this, gameList);
        recyclerView.setAdapter(gameAdapter);
    }
}