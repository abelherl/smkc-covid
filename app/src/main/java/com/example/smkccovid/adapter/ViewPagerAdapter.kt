package com.example.smkccovid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smkccovid.DashboardFragment
import com.example.smkccovid.StatsFragment
import com.example.smkccovid.SettingsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val JUMLAH_MENU = 3
    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> { return StatsFragment()
            }
            1 -> { return DashboardFragment()
            }
            2 -> { return SettingsFragment()
            }
            else -> {
                return DashboardFragment()
            }
        }
    }
    override fun getItemCount(): Int {
        return JUMLAH_MENU
    }
}