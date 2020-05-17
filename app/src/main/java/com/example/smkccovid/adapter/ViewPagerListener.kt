package com.example.smkccovid.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener


class ViewPagerListener : SimpleOnPageChangeListener() {
    var currentPage = 0
        private set

    override fun onPageSelected(position: Int) {
        currentPage = position
        Log.d("PAGER_LISTENER", position.toString())
    }
}