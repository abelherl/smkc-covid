package com.example.smkccovid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smkccovid.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_stats.*


class StatsFragment : Fragment() {

    private lateinit var viewPagerAdapter : ViewPagerAdapter

    private val titles = arrayOf("Comparison", "Global", "Philippines")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initView() {
        viewPagerAdapter =
            ViewPagerAdapter(this)
        vp_stats.adapter = viewPagerAdapter
        vp_stats.setCurrentItem(1,false)
        TabLayoutMediator(tl_stats, vp_stats,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0) {
                    tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_pie_chart_black_24dp)
                }
                else {
                    tab.text = titles[position]
                }
            }
        ).attach()

        val layout = (tl_stats.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0.3F
        layout.layoutParams = layoutParams
        layout.invalidate()
    }
}
