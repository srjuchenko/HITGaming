package com.example.hitgaming.services;

import com.example.hitgaming.models.APIResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameDataService {
    @GET("games")
    Call<APIResult> getResults(@Query("key") String apiKey, @Query("page_size") int pageSize);
}
