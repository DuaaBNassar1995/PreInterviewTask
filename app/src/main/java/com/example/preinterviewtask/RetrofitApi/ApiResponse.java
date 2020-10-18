package com.example.preinterviewtask.RetrofitApi;

import com.example.preinterviewtask.Object.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiResponse {

    @GET("photos")
    Call<List<Item>> getItems(@Query("page") int page, @Query("pagesize") int pagesize) ;
}


