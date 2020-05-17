package com.example.smkccovid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_signal.*

class SignalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signal)
        initView()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        AcTrans.Builder(this).performFade()
    }

    fun initView() {
        bt_exit.setOnClickListener {
            goTo(SplashActivity(), true)
        }
    }

    fun goTo(activity: AppCompatActivity, finish: Boolean) {
        startActivity(Intent(this, activity::class.java))
        if (finish) { finish() }
        AcTrans.Builder(this).performFade()
    }
}
