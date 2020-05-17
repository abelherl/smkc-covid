package com.example.smkccovid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.smkccovid.*

class DetailPagerAdapter(fragment : FragmentManager, nContext: Context) : FragmentPagerAdapter(fragment) {
    private val tabCount = 3
    var context = nContext

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                DetailFragment1()
            }
            1 -> {
                DetailFragment1()
            }
            2 -> {
                DetailFragment1()
            }
            else -> {
                DashboardFragment()
            }
        }
    }
}