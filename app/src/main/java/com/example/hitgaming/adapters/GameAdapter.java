package com.example.hitgaming.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hitgaming.R;
import com.example.hitgaming.models.Game;
import com.example.hitgaming.models.GameResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<GameResult> gameList;
    private Context context;

    public GameAdapter(Context context, List<GameResult> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        GameResult game = gameList.get(position);

        holder.gameTitleTextView.setText(game.getName());


        Glide.with(context)
                .load(game.getImage().getOriginalUrl())
                .into(holder.gameImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO show the game details activity!!!
                System.out.println("the item was clicked" + game.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleTextView;
        ImageView gameImageView;

        public GameViewHolder(View itemView) {
            super(itemView);
            gameTitleTextView = itemView.findViewById(R.id.textViewGameTitle);
            gameImageView = itemView.findViewById(R.id.imageViewGame);
        }
    }

}
