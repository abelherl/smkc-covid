package com.example.smkccovid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.activity_splash.*
import render.animations.Render
import render.animations.Zoom
import util.isInternetAvailable
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

    override fun onBackPressed() { }

    fun initView() {
        val bool = isInternetAvailable(this)
        Handler().postDelayed({
            if (bool) {
                dismissLoading()
                goTo(MainActivity(), true)
            } else {
                goTo(SignalActivity(), false)
            }
        }, SPLASH_TIME_OUT)
    }

    fun goTo(activity: AppCompatActivity, finish: Boolean) {
        startActivity(Intent(this, activity::class.java))
        if (finish) { finish() }
        AcTrans.Builder(this).performFade()
    }

    fun dismissLoading() {
        val render = Render(this)

        render.setDuration(500)

        render.setAnimation(Zoom().Out(iv_splash_spin))
        render.start()
    }
}
