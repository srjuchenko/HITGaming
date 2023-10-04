package com.example.hitgaming.services;

import com.example.hitgaming.models.APIResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameMovieServiceByID {
    @GET("games/{id}/movies")
    Call<APIResult> getResults(@Path("id") String id, @Query("key") String apiKey);
}

