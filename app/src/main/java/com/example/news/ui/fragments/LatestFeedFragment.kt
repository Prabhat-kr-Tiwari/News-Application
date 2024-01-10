package com.example.news.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.Utils.Resource
import com.example.news.ViewModelProviderFactory.NewsViewModelProviderFactory
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentLatestFeedBinding
import com.example.news.repository.NewsRepository
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.news.Utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.news.ui.DetailScreenActivity

import com.example.news.viewModel.NewsViewModel



class LatestFeedFragment : Fragment() {
    lateinit var binding:FragmentLatestFeedBinding
    lateinit var viewModel:NewsViewModel
    lateinit var newsAdapter: NewsAdapter
     val TAG="LatestFeedFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLatestFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {

            Log.d(TAG, "onViewCreated: $it")
            val intent = Intent(requireContext(), DetailScreenActivity::class.java)
            val bundle = Bundle()
            // Add data to the bundle
            bundle.putSerializable("key", it)
            // Put the bundle into the intent
            intent.putExtras(bundle)
            // Start the activity with the intent
            requireContext().startActivity(intent)

        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewPage == totalPages
                        if(isLastPage) {
                        binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                    }

                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let {message->
                        Log.d(TAG, "onViewCreated: An error occured $message")


                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }

            }
        })


    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
        isLoading=false

    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility=View.VISIBLE
        isLoading=true

    }

    //pagination
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    //pagination end
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@LatestFeedFragment.scrollListener)
        }

    }
}