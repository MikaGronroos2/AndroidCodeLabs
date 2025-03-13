package com.example.retrofitexercise

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("w/api.php")
    suspend fun search(
        @Query("action") action: String,
        @Query("format") format: String,
        @Query("list") list: String,
        @Query("srsearch") srsearch: String
    ): WikipediaResponse
}
