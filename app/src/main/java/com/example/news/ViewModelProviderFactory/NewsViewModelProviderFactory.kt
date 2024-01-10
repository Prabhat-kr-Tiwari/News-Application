package com.example.news.ViewModelProviderFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.repository.NewsRepository
import com.example.news.viewModel.NewsViewModel

class NewsViewModelProviderFactory(val newsRepository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository ) as T
    }
}