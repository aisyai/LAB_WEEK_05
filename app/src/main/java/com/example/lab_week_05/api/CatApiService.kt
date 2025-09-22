package com.example.lab_week_05.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("images/search")
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("size") format: String
    ): Call<String>
}