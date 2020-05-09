package com.example.smkccovid

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val JUMLAH_MENU = 2
    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> { return FriendsFragment() }
            1 -> { return DashboardFragment() }
            else -> {
                return DashboardFragment()
            }
        }
    }
    override fun getItemCount(): Int {
        return JUMLAH_MENU
    }
}