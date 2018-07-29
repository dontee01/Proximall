package com.example.proxymall.rest;

import com.example.proxymall.model.GetCategoryContent;
import com.example.proxymall.model.GetMalls;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/expore/get-categories")
    Call<List<GetMalls>> getMalls(@Query("appId") int appId);

    @GET("movie/{id}")
    Call<GetCategoryContent> getCategoryContent(@Path("id") int id, @Query("api_key") String apiKey);
}
