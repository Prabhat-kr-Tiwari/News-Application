package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.ViewModelProviderFactory.NewsViewModelProviderFactory
import com.example.news.databinding.ActivityMainBinding
import com.example.news.repository.NewsRepository
import com.example.news.viewModel.NewsViewModel

class MainActivity : AppCompatActivity() {


//      lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView=binding.bottomNavigationView
        val navController=findNavController(R.id.newsNavHostFragment)
        navView.setupWithNavController(navController)


        val newsRepository =NewsRepository()
        val viewModelProviderFactory=NewsViewModelProviderFactory(newsRepository)
//        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)





    }
}