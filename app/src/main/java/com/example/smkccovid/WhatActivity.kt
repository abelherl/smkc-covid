package com.example.smkccovid

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_what.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import util.loadLocale

class WhatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale(this)
        setContentView(R.layout.activity_what)
        initView()
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        loadLocale(this)
//        super.onConfigurationChanged(newConfig)
//    }

    fun initView() {
        OverScrollDecoratorHelper.setUpOverScroll(sv_what)
        bt_back.setOnClickListener { buttonBack() }
        bt_close.setOnClickListener { buttonBack() }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }
}
