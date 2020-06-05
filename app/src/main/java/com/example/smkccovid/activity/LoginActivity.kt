package com.example.smkccovid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smkccovid.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_web_view.*
import util.goTo

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        bt_back_login.setBackgroundColor(Color.TRANSPARENT)
        bt_sign.setOnClickListener { buttonSign() }
        et_email.clearFocus()
    }

    private fun buttonSign() {
        goTo(this, MainActivity(), true, null)
    }
}
