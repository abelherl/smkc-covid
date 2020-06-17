package com.example.smkccovid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_web_view.*
import util.goTo

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
        bt_back_login.setBackgroundColor(Color.TRANSPARENT)
        bt_tap.setBackgroundColor(Color.TRANSPARENT)
        bt_back_login.setOnClickListener { buttonBack() }
        bt_tap.setOnClickListener { buttonTap() }
        bt_sign.setOnClickListener { buttonSign() }
        et_email.clearFocus()
    }

    private fun buttonSign() {
        goTo(this, MainActivity(), true, null)
    }

    private fun buttonTap() {
        goTo(this, ForgotActivity(), false, null)
    }
}
