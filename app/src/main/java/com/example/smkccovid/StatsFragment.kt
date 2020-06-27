package com.example.smkccovid

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smkccovid.adapter.ViewPagerAdapter
import com.example.smkccovid.adapter.ViewPagerListener
import com.example.smkccovid.behavior.ZoomOutTransition
import kotlinx.android.synthetic.main.fragment_stats.*
import util.disableTouch
import util.enableTouch


class StatsFragment : Fragment() {

    private lateinit var viewPagerAdapter : ViewPagerAdapter

    private var titles = arrayOf("")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    fun initView() {
        val edit = requireContext().getSharedPreferences("test", 0).edit()
        edit.putInt("loaded", 0)
        edit.apply()

        disableTouch(requireActivity(), R.id.fl_load_stats, R.id.bottomNavMain, true)

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, requireContext())
        tl_stats.setupWithViewPager(vp_stats)
        vp_stats.addOnPageChangeListener(ViewPagerListener())
        vp_stats.offscreenPageLimit = 10
        vp_stats.setPageTransformer(false, ZoomOutTransition())
        vp_stats.adapter = viewPagerAdapter
    }
}
