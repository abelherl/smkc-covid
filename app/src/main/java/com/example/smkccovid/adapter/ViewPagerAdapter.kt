package com.example.smkccovid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smkccovid.CountryFragment
import com.example.smkccovid.DashboardFragment
import com.example.smkccovid.GlobalFragment
import com.example.smkccovid.SettingsFragment


class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val tabCount = 3

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {
                GlobalFragment()
            }
            1 -> {
                GlobalFragment()
            }
            2 -> {
                CountryFragment()
            }
            else -> {
                DashboardFragment()
            }
        }
    }
}