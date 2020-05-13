package com.example.smkccovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
//    val menuTeks = arrayOf(R.string.stats, R.string.summary, R.string.settings)
//    val menuIcon = arrayOf(R.drawable.ic_stats, R.drawable.ic_dashboard2, R.drawable.ic_settings_black_24dp)

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val adapter = ViewPagerAdapter(this)
//        view_pager.adapter = adapter
//        view_pager.currentItem = 1
//        TabLayoutMediator(tab_layout, view_pager,
//            TabConfigurationStrategy { tab, position ->
////                tab.text = getString(menuTeks[position])
//                tab.icon = ResourcesCompat.getDrawable(resources, menuIcon[position], null)
//
//            }).attach()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavMain.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuStats -> {
                    loadFragment(StatsFragment())
                }
                R.id.menuDashboard -> {
                    loadFragment(DashboardFragment())
                }
                R.id.menuSettings -> {
                    loadFragment(SettingsFragment())
                } else -> false
            }
        }
        bottomNavMain.selectedItemId = R.id.menuDashboard
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, fragment)
            .commit()
        return true
    }
}
