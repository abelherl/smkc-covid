package com.example.smkccovid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.voela.actrans.AcTrans
import util.loadLocale
import util.setLocale
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    fun initView() {
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            AcTrans.Builder(this).performFade()
        }, SPLASH_TIME_OUT)
    }
}
