package com.example.smkccovid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.smkccovid.*


class ViewPagerAdapter(fragment : FragmentManager, nContext: Context) : FragmentPagerAdapter(fragment) {
    private val tabCount = 3
    var context = nContext

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return RankFragment1()
            1 -> return RankFragment2()
            2 -> return RankFragment3()
            else -> return RankFragment1()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.resources.getString(R.string.cases)
            1 -> context.getString(R.string.recovered)
            else -> { return context.getString(R.string.deaths) }
        }
    }
}