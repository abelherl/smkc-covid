package com.example.smkccovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {


    /*

    Fake splash screen because I still don't know how to apply the real one smh
    Aaaand I don't think a real splash screen is necessary for the current stage?
    Anyhow I would LOVE to learn how to create splash screen THE RIGHT WAY.

     */

    private val SPLASH_TIME_OUT:Long = 2000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
