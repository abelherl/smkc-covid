package com.example.smkccovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
//    val menuTeks = arrayOf(R.string.stats, R.string.dashboard, R.string.settings)
    val menuIcon = arrayOf(R.drawable.ic_stats, R.drawable.ic_dashboard2, R.drawable.ic_settings_black_24dp)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        view_pager.setAdapter(adapter);
        TabLayoutMediator(tab_layout, view_pager,
            TabConfigurationStrategy { tab, position ->
//                tab.text = getString(menuTeks[position])
                tab.icon = ResourcesCompat.getDrawable(resources, menuIcon[position], null)

            }).attach()
    }
}
