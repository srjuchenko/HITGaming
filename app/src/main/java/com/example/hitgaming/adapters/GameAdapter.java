package com.example.hitgaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hitgaming.R;
import com.example.hitgaming.activities.GameDetailsActivity;
import com.example.hitgaming.models.GameResult;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<GameResult> gameList;
    private Context context;

    public GameAdapter(Context context, List<GameResult> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
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
                .load(game.getBackgroundImage())
                .into(holder.gameImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, GameDetailsActivity.class);

            // Pass any relevant data to the GameDetailsActivity using Intent extras
            intent.putExtra("gameName", game.getName()); // Example: Pass the game ID
            intent.putExtra("gameImg", game.getBackgroundImage());
            intent.putExtra("gameRating", game.getRating().toString());
            intent.putExtra("gameRelease", game.getReleased());
            intent.putExtra("gameId", game.getId().toString());

            // Start the GameDetailsActivity
            context.startActivity(intent);
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
