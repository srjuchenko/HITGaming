package com.example.hitgaming.services;

import com.example.hitgaming.models.GameResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameInfoServiceByID {
    @GET("games/{id}")
    Call<GameResult> getInfo(@Path("id") String id, @Query("key") String apiKey);
}
