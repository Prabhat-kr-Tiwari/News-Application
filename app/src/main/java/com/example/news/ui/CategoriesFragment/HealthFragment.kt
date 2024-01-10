package com.example.news.ui.CategoriesFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.Utils.Resource
import com.example.news.ViewModelProviderFactory.NewsViewModelProviderFactory
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentEntertainmentBinding
import com.example.news.databinding.FragmentHealthBinding
import com.example.news.repository.NewsRepository
import com.example.news.viewModel.NewsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HealthFragment : Fragment() {
    lateinit var binding: FragmentHealthBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG="HealthFragment"
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHealthBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        setUpRecyclerView()



        viewModel.healthCategoryNews.observe(viewLifecycleOwner, Observer {response->
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HealthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HealthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}