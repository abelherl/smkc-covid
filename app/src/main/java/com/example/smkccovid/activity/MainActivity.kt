package com.example.smkccovid.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smkccovid.DashboardFragment
import com.example.smkccovid.R
import com.example.smkccovid.SettingsFragment
import com.example.smkccovid.StatsFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_main.*
import util.setLocale
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onBackPressed() { }

    fun initView() {
        FirebaseMessaging.getInstance().subscribeToTopic("COVID")
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
            })


        setSharedPref(this.getSharedPreferences("test", 0).getString("lang", "en")!!)
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
        AcTrans.Builder(this).performFade()
        return true
    }

    fun setSharedPref(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val test by lazy { this.getSharedPreferences("test", Context.MODE_PRIVATE) }
        val edit = test.edit()
        Log.d("SHAREDPREF1", test.getString("lang", "")!!)
        Log.d("SHAREDPREF2", Locale.getDefault().language)
        edit.putString("lang", lang)
        edit.apply()
        setLocale(this, test.getString("lang", "in")!!)
        Log.d("SHAREDPREF3", test.getString("lang", "")!!)
        Log.d("SHAREDPREF4", Locale.getDefault().language)
        val config: Configuration = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
//        changeLanguage(this)
    }

    fun changeLanguage(context: Context) {
        Handler().post {
            val intent = Intent(context, MainActivity()::class.java)
            startActivity(intent)
        }
    }
}
