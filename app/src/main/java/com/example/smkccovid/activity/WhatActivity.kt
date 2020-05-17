package com.example.smkccovid.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_what.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*

class WhatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what)
        initView()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context? {
        val language: String = context.getSharedPreferences("test", 0).getString("lang", "")!!
        val locale = Locale(language)
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context? {
        val resources: Resources = context.resources
        val configuration: Configuration = resources.getConfiguration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
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
