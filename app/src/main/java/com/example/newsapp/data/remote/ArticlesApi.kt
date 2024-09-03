package com.example.newsapp.data.remote

import com.example.newsapp.data.model.ArticlesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("category/{category}/{countryCode}.json")
    suspend fun getTopHeadlines(
        @Path("category") category: String,
        @Path("countryCode") countryCode: String
    ): ArticlesDto
}