package com.example.smkccovid.activity

import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_splash.*
import render.animations.Attention
import render.animations.Render
import render.animations.Zoom
import util.isInternetAvailable

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    override fun onBackPressed() { }

    fun initView() {
        val view = iv_splash
        Handler().postDelayed({
            val bool = isInternetAvailable(this)
            if (bool) {
                continueTo(MainActivity(), Attention().Pulse(view), 400, true)
            } else {
                continueTo(SignalActivity(), Attention().Flash(view), 500, false)
            }
        }, SPLASH_TIME_OUT)
    }

    fun goTo(activity: AppCompatActivity, finish: Boolean) {
        startActivity(Intent(this, activity::class.java))
        if (finish) { finish() }
        AcTrans.Builder(this).performFade()
    }

    fun continueTo(activity: AppCompatActivity, animation: AnimatorSet, duration: Long, finish: Boolean) {
        animate(animation, duration)
        dismissLoading(duration)
        Handler().postDelayed({
            animate(animation, duration)
            Handler().postDelayed({
                goTo(activity, finish)
            }, duration+100)
        }, duration+100)
    }

    fun animate(animation: AnimatorSet, duration: Long) {
        val render = Render(this)

        render.setDuration(duration)

        render.setAnimation(animation)
        render.start()
    }

    fun dismissLoading(duration: Long) {
        val render = Render(this)

        render.setDuration(duration)

        render.setAnimation(Zoom().Out(iv_splash_spin))
        render.start()
    }
}
