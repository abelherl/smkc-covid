package com.example.smkccovid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.voela.actrans.AcTrans

class WhatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }
}
