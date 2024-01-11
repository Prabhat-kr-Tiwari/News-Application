package com.example.news.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.ui.CategoriesFragment.BusinessFragment
import com.example.news.ui.CategoriesFragment.EntertainmentFragment
import com.example.news.ui.CategoriesFragment.HealthFragment
import com.example.news.ui.CategoriesFragment.ScienceFragment
import com.example.news.ui.CategoriesFragment.SportsFragment
import com.example.news.ui.CategoriesFragment.TechnologyFragment

/*class ViewPagerAdapter {

}*/
public class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0-> BusinessFragment()
            1-> EntertainmentFragment()
            2->HealthFragment()
            3->ScienceFragment()
            4->SportsFragment()
            5->TechnologyFragment()
            else-> BusinessFragment()
        }

    }

}
