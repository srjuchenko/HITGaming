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

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> gameList; // Replace 'Game' with your actual game model class
    private Context context;

    public GameAdapter(Context context, List<Game> gameList) {
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
        Game game = gameList.get(position);

        // Bind game data to the ViewHolder views
        holder.gameTitleTextView.setText(game.getGameName());

        // Load game image using Glide or another image loading library
        Glide.with(context)
                .load(game.getImageURL())
                .into(holder.gameImageView);

        // Add click listener if needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click (e.g., show game details)
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
