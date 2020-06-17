package com.example.smkccovid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_forgot.*
import kotlinx.android.synthetic.main.activity_register.*
import util.goTo

class ForgotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        initView()
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun initView() {
        bt_back_forgot.setBackgroundColor(Color.TRANSPARENT)
        bt_back_forgot.setOnClickListener { buttonBack() }
        bt_submit.setOnClickListener { buttonSign() }
    }

    private fun buttonSign() {
        goTo(this, LoginActivity(), true, null)
    }
}
