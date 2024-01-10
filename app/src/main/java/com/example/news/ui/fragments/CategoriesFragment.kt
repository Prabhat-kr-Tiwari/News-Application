package com.example.news.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.ViewPagerAdapter.ViewPagerAdapter
import com.example.news.databinding.FragmentCategoriesBinding
import com.google.android.material.tabs.TabLayout

//apikey=0e40a88fcc5d49b1a4fc8bf7b3881be1
class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCategoriesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //viewpager
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewpager
        val viewPagerAdapter = ViewPagerAdapter(this)

        viewPager.adapter = viewPagerAdapter
        tabLayout.let { tabLayout ->
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.getTabAt(position)?.select()
                }
            })

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        viewPager.currentItem = it.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Handle unselected tab
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Handle tab reselection
                    tab?.let {
                        viewPager.currentItem = it.position
                    }

                }
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.getTabAt(position)?.select()
                }
            })


        }
    }



}