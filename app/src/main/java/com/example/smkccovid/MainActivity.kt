package com.example.smkccovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
//    val menuTeks = arrayOf("Stats", "Dashboard")
    val menuIcon = arrayOf(R.drawable.ic_stats, R.drawable.ic_dashboard)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        view_pager.setAdapter(adapter);
        TabLayoutMediator(tab_layout, view_pager,
            TabConfigurationStrategy { tab, position ->
//                tab.text = menuTeks[position]
                tab.icon = ResourcesCompat.getDrawable(resources, menuIcon[position], null)

            }).attach()
    }
}
