package com.example.news.repository

import com.example.news.api.RetrofitInstance


class NewsRepository {


    suspend fun getBreakingNews(countryCode: String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    //get news based on category
    suspend fun getNewsBasedOnCategory(countryCode: String,pageNumber: Int,category:String)=
        RetrofitInstance.api.getNewsBasedOnCategory(countryCode,pageNumber,category)

}