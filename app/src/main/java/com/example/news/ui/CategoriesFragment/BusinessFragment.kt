package com.example.news.ui.CategoriesFragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.Utils.Resource
import com.example.news.ViewModelProviderFactory.NewsViewModelProviderFactory
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentBusinessBinding
import com.example.news.repository.NewsRepository
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



        viewModel.basedOnCategoryNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

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

    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility=View.VISIBLE

    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }

    }


}