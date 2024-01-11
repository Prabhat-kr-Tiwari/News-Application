package com.example.news.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Application.NewsApplication
import com.example.news.Utils.Resource
import com.example.news.model.NewsResponse
import com.example.news.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class NewsViewModel(application: Application,
                    val newsRepository: NewsRepository
) : AndroidViewModel(application) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null


    //based on category for business
    val basedOnCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var basedOnCategoryNewsPage = 1
    var category = "business"
    var basedOnCategoryResponse: NewsResponse? = null


    //based on category for entertainment
    val entertainmentCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var entertainmentCategoryNewsPage = 1
    var entertainmentcategory = "entertainment"
    var entertainmentcategoryResponse: NewsResponse? = null

    //based on category for health
    val healthCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var healthCategoryNewsPage = 1
    var healthcategory = "health"
    var healthcategoryResponse: NewsResponse? = null

    ////based on category for science
    val scienceCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var scienceCategoryNewsPage = 1
    var sciencecategory = "science"
    var sciencecategoryResponse:NewsResponse?=null

    ////based on category for sports
    val sportsCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sportsCategoryNewsPage = 1
    var sportscategory = "sports"
    var sportscategoryResponse:NewsResponse?=null

    //based on category for technology
    val technologyCategoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var technologyCategoryNewsPage = 1
    var technologycategory = "technology"
    var technologycategoryResponse:NewsResponse?=null


    init {
        getBreakingNews("us")
        getBreakingNewsBasedOnCategory("us")
        getNewsBasedOnEntertainmentCategory("us")
        getNewsBasedOnHealthCategory("us")
        getNewsBasedOnScienceCategory("us")
        getNewsBasedOnSportsCategory("us")
        getNewsBasedOnTechnologyCategory("us")

    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
       safeBreakingNewsCall(countryCode)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun getBreakingNewsBasedOnCategory(countryCode: String) = viewModelScope.launch {
        safeBreakingNewBasedOnCategoryCall(countryCode)
    }

    private fun handleBreakingNewsBasedOnCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                basedOnCategoryNewsPage++
                if (basedOnCategoryResponse == null) {
                    basedOnCategoryResponse = resultResponse
                } else {
                    val oldArticles = basedOnCategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(basedOnCategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //entairnment
    fun getNewsBasedOnEntertainmentCategory(countryCode: String) = viewModelScope.launch {
        safeEntertainmentNewBasedOnCategoryCall(countryCode)
    }

    private fun handleNewsBasedOnEntertainmentCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                entertainmentCategoryNewsPage++
                if (entertainmentcategoryResponse == null) {
                    entertainmentcategoryResponse = resultResponse
                } else {
                    val oldArticles = entertainmentcategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(entertainmentcategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //health
    fun getNewsBasedOnHealthCategory(countryCode: String) = viewModelScope.launch {
        safeHealthNewBasedOnCategoryCall(countryCode)
    }

    private fun handleNewsBasedOnHealthCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                healthCategoryNewsPage++
                if (healthcategoryResponse == null) {
                    healthcategoryResponse = resultResponse
                } else {
                    val oldArticles = healthcategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(healthcategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //Science
    fun getNewsBasedOnScienceCategory(countryCode: String) = viewModelScope.launch {
        safeScienceNewBasedOnCategoryCall(countryCode)
    }

    private fun handleNewsBasedOnScienceCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                scienceCategoryNewsPage++
                if (sciencecategoryResponse == null) {
                    sciencecategoryResponse = resultResponse
                } else {
                    val oldArticles = sciencecategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(sciencecategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //Sports
    fun getNewsBasedOnSportsCategory(countryCode: String) = viewModelScope.launch {
        safeSportsNewBasedOnCategoryCall(countryCode)
    }

    private fun handleNewsBasedOnSportsCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                sportsCategoryNewsPage++
                if (sportscategoryResponse == null) {
                    sportscategoryResponse = resultResponse
                } else {
                    val oldArticles = sportscategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(sportscategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //Technology
    fun getNewsBasedOnTechnologyCategory(countryCode: String) = viewModelScope.launch {
        safeTechnologyNewBasedOnCategoryCall(countryCode)
    }

    private fun handleNewsBasedOnTechnologyCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                technologyCategoryNewsPage++
                if (technologycategoryResponse == null) {
                    technologycategoryResponse = resultResponse
                } else {
                    val oldArticles = technologycategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(technologycategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    private suspend fun safeSearchNewsCall(searchQuery: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    //business
    private suspend fun safeBreakingNewBasedOnCategoryCall(countryCode: String) {
        basedOnCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,basedOnCategoryNewsPage,category)
                basedOnCategoryNews.postValue(handleBreakingNewsBasedOnCategoryNewsResponse(response))
            } else {
                basedOnCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> basedOnCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    //entairtainment
    private suspend fun safeEntertainmentNewBasedOnCategoryCall(countryCode: String) {
        entertainmentCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,entertainmentCategoryNewsPage,entertainmentcategory)
                entertainmentCategoryNews.postValue(handleNewsBasedOnEntertainmentCategoryNewsResponse(response))
            } else {
                entertainmentCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> entertainmentCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }

    private suspend fun safeHealthNewBasedOnCategoryCall(countryCode: String) {
        healthCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,healthCategoryNewsPage,healthcategory)
                healthCategoryNews.postValue(handleNewsBasedOnHealthCategoryNewsResponse(response))
            } else {
                healthCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> healthCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    private suspend fun safeScienceNewBasedOnCategoryCall(countryCode: String) {
        scienceCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,scienceCategoryNewsPage,sciencecategory)
                scienceCategoryNews.postValue(handleNewsBasedOnScienceCategoryNewsResponse(response))
            } else {
                scienceCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> scienceCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    private suspend fun safeSportsNewBasedOnCategoryCall(countryCode: String) {
        sportsCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,sportsCategoryNewsPage,sportscategory)
                sportsCategoryNews.postValue(handleNewsBasedOnSportsCategoryNewsResponse(response))
            } else {
                sportsCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> sportsCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }
    private suspend fun safeTechnologyNewBasedOnCategoryCall(countryCode: String) {
        technologyCategoryNews.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()) {
                val response = newsRepository.getNewsBasedOnCategory(countryCode,technologyCategoryNewsPage,technologycategory)
                technologyCategoryNews.postValue(handleNewsBasedOnTechnologyCategoryNewsResponse(response))
            } else {
                technologyCategoryNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> technologyCategoryNews.postValue(Resource.Error("Network Failure"))

            }
        }
    }


    private fun checkInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("CHECKPRABHAT", "checkInternetConnection: Here")
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            //these are not deprecated at api level below 23
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}