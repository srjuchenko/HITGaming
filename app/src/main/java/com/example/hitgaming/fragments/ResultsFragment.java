package com.example.hitgaming.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitgaming.R;
import com.example.hitgaming.adapters.GameAdapter;
import com.example.hitgaming.models.Game;
import com.example.hitgaming.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ResultsFragment extends Fragment {
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gameList = new ArrayList<>();
    public ResultsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        Game game1 = new Game("game" ,"2012", Constants.IMG_URL, 4.2f);


        for (int i=0; i<20; i++) {
            gameList.add(game1);
        }

        recyclerView = view.findViewById(R.id.games_items_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gameAdapter = new GameAdapter(getActivity(), gameList);


        recyclerView.setAdapter(gameAdapter);
        return view;
    }
}