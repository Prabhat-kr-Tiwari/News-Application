package com.example.news.api

import com.example.news.Utils.Constants.Companion.API_KEY
import com.example.news.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsBasedOnCategory(
        @Query("country")
        countryCode: String="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>


}