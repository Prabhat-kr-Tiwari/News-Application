package com.example.news.ui.CategoriesFragment

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.Utils.Constants
import com.example.news.Utils.Resource
import com.example.news.ViewModelProviderFactory.NewsViewModelProviderFactory
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentBusinessBinding
import com.example.news.repository.NewsRepository
import com.example.news.ui.DetailScreenActivity
import com.example.news.ui.fragments.DetailNewsFragment
import com.example.news.viewModel.NewsViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessFragment : Fragment() {
    lateinit var binding: FragmentBusinessBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG="BusinessFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBusinessBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        setUpRecyclerView()


       /* newsAdapter.setOnItemClickListener {
            Log.d(TAG, "onViewCreated: $it")

            val bundle=Bundle().apply {
                putString("URL",it.url)
            }
            Log.d(TAG, "onViewCreated: CLCIKED")
            listener?.navigateToFragmentC(bundle)


        }*/
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


        viewModel.basedOnCategoryNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewPage == totalPages
                        if(isLastPage) {
                            binding.rvNews.setPadding(0, 0, 0, 0)
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
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getBreakingNewsBasedOnCategory("us")
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
        binding.rvNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
            addOnScrollListener(this@BusinessFragment.scrollListener)
        }

    }
    private var listener: FragmentBListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? FragmentBListener
    }
    interface FragmentBListener {
        fun navigateToFragmentC(data:Bundle)
    }






}