package com.example.smkccovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
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
