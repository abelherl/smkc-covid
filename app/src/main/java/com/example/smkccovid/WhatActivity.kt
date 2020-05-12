package com.example.smkccovid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_what.*

class WhatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what)

        bt_back.setOnClickListener { buttonBack() }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }
}
