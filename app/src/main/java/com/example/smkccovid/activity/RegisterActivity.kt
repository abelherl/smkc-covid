package com.example.smkccovid.activity

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.smkccovid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.bt_sign
import kotlinx.android.synthetic.main.activity_register.*
import util.goTo

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        bt_sign_register.setOnClickListener { buttonSign() }
        bt_tap_register.setOnClickListener { buttonTap() }
        et_email_register.clearFocus()
    }

    private fun buttonSign() {
        goTo(this, LoginActivity(), true, null)
    }

    private fun buttonTap() {
        goTo(this, LoginActivity(), false, null)
    }
}
