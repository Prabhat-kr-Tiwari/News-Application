package com.example.news.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Utils.Resource
import com.example.news.model.NewsResponse
import com.example.news.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

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
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
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
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
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
        basedOnCategoryNews.postValue(Resource.Loading())
        val response =
            newsRepository.getNewsBasedOnCategory(countryCode, basedOnCategoryNewsPage, category)
        basedOnCategoryNews.postValue(handleBreakingNewsBasedOnCategoryNewsResponse(response))
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
        entertainmentCategoryNews.postValue(Resource.Loading())
        val response = newsRepository.getNewsBasedOnCategory(
            countryCode,
            entertainmentCategoryNewsPage,
            entertainmentcategory
        )
        entertainmentCategoryNews.postValue(
            handleNewsBasedOnEntertainmentCategoryNewsResponse(
                response
            )
        )
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
        healthCategoryNews.postValue(Resource.Loading())
        val response = newsRepository.getNewsBasedOnCategory(
            countryCode,
            healthCategoryNewsPage,
            healthcategory
        )
        healthCategoryNews.postValue(handleNewsBasedOnHealthCategoryNewsResponse(response))
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
        scienceCategoryNews.postValue(Resource.Loading())
        val response = newsRepository.getNewsBasedOnCategory(
            countryCode,
            scienceCategoryNewsPage,
            sciencecategory
        )
        scienceCategoryNews.postValue(handleNewsBasedOnScienceCategoryNewsResponse(response))
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
        sportsCategoryNews.postValue(Resource.Loading())
        val response = newsRepository.getNewsBasedOnCategory(
            countryCode,
            sportsCategoryNewsPage,
            sportscategory
        )
        sportsCategoryNews.postValue(handleNewsBasedOnSportsCategoryNewsResponse(response))
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
        technologyCategoryNews.postValue(Resource.Loading())
        val response = newsRepository.getNewsBasedOnCategory(
            countryCode,
            technologyCategoryNewsPage,
            technologycategory
        )
        technologyCategoryNews.postValue(handleNewsBasedOnTechnologyCategoryNewsResponse(response))
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


}