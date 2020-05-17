package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.activity.ChooseActivity
import com.example.smkccovid.activity.DetailActivity
import com.example.smkccovid.activity.WhatActivity
import com.example.smkccovid.adapter.NewsAdapter
import com.example.smkccovid.data.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_stats.*
import render.animations.Attention
import render.animations.Bounce
import render.animations.Fade
import render.animations.Render
import retrofit2.Call
import retrofit2.Response
import util.*
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    lateinit var listCountry : List<NewCountryItem>

    private fun simulateNews() {
        var listTeman = ArrayList<Country>()
        for (i in 0..10) {
            listTeman.add(Country("1", "2"))
        }
        rv_news.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_news.adapter = NewsAdapter(activity!!, listTeman)
    }

    private fun callApiGetData(sharedPreferences: SharedPreferences) {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
        val call = apiRequest.getGlobal()

        val slug = context!!.getSharedPreferences("test", 0).getString("slug", "indonesia")

        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
                tampilToast(context!!, "Gagal")
                callApiGetData(sharedPreferences)
            }
            override fun onResponse(call: Call<Summary>, response:
            Response<Summary>
            ) {
                when {
                    response.isSuccessful -> setData(response.body()!!.globalSummary, response.body()!!.countries.find { it.slug.contains(slug!!.toLowerCase()) }!!, sharedPreferences)
                    else -> {
                        tampilToast(context!!, response.message())
                        callApiGetData(sharedPreferences)
                    }
                }
            }
        })
    }

    private fun configureRv() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    private fun initView() {
        disableTouch(activity!!, R.id.bottomNavMain, true)

        val sharedPreferences = context!!.getSharedPreferences("test", 0)

        val edit = sharedPreferences.edit()
        edit.putInt("loaded", 0)
        edit.apply()

        val update: Boolean = sharedPreferences.getBoolean("update", true)
        callApiGetData(sharedPreferences)
        simulateNews()

        bt_sync.setOnClickListener { buttonSync() }
        bt_what.setOnClickListener { buttonWhat() }
        bt_edit.setOnClickListener { buttonCountry() }

        ll_blue1.setOnClickListener { goToWithBoolean(true, 0) }
        ll_blue2.setOnClickListener { goToWithBoolean(false, 0) }

        ll_green1.setOnClickListener { goToWithBoolean(true, 1) }
        ll_green2.setOnClickListener { goToWithBoolean(false, 1) }

        ll_red1.setOnClickListener { goToWithBoolean(true, 2) }
        ll_red2.setOnClickListener { goToWithBoolean(false, 2) }
    }

    private fun goToWithBoolean(boolean: Boolean, extra: Int) {
        val edit = context!!.getSharedPreferences("test", 0).edit()
        edit.putBoolean("global", boolean)
        edit.apply()
        goTo(context!!, DetailActivity(), false, extra)
    }

    fun changelanguage(context: Context) {
        //String lang = "hi_IN";
        //  Locale locale = new Locale(lang);
        val locale = Locale("in")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.configuration.setLocale(locale)
    }

    private fun setData(global: WorldWeeklyItem, country: CountrySummary, sharedPreferences: SharedPreferences) {
        enableTouch(activity!!, R.id.fl_load_main, R.id.bottomNavMain, true)

        val edit = sharedPreferences.edit()
        edit.putInt("confirmed", global.totalConfirmed)
        edit.putBoolean("update", false)
        edit.apply()

        tv_country_dashboard.text = country.country

        num_blue.text = prettyCount(global.totalConfirmed)
        num_green.text = prettyCount(global.totalRecovered)
        num_red.text = prettyCount(global.totalDeaths)

        num_blue2.text = "+" + global.newConfirmed
        num_green2.text = "+" + global.newRecovered
        num_red2.text = "+" + global.newDeaths

        num_blue3.text = prettyCount(country.totalConfirmed)
        num_green3.text = prettyCount(country.totalRecovered)
        num_red3.text = prettyCount(country.totalDeaths)

        num_blue4.text = "+" + country.newConfirmed
        num_green4.text = "+" + country.newRecovered
        num_red4.text = "+" + country.newDeaths

        num_blue.invalidate()
        num_blue2.invalidate()
        num_blue3.invalidate()
        num_blue4.invalidate()

        num_green.invalidate()
        num_green2.invalidate()
        num_green3.invalidate()
        num_green4.invalidate()

        num_red.invalidate()
        num_red2.invalidate()
        num_red3.invalidate()
        num_red4.invalidate()

        tv_country_dashboard.invalidate()

//        tampilToast(context!!, country.totalConfirmed.toString())
        dismissLoading(context!!, sp_main)
    }

    fun prettyCount(number: Number): String? {
        val sharedPref = context!!.getSharedPreferences("test", 0)
        val lang = sharedPref.getString("lang", "en")

        var suffix = arrayOf("", "K", "M", "B")

        if (lang == "in") {
            suffix = arrayOf("", "rb", "jt", "m")
        }

        val numValue: Long = number.toLong()
        val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / Math.pow(
                    10.0,
                    base * 3.toDouble()
                )
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun buttonSync() {

//        bt_sync.animate().rotation(bt_sync.rotation-360).start()

        val render = Render(activity!!)

        render.setDuration(500)

        render.setAnimation(Attention().Wobble(bt_sync))
        render.start()

        val render1 = Render(activity!!)

        render1.setDuration(300)

        render1.setAnimation(Fade().OutLeft(ll_summary))
        render1.start()

        val render2 = Render(activity!!)

        render2.setDuration(400)

        render2.setAnimation(Fade().Out(ll_total))
        render2.start()

        Handler().postDelayed({
            val render3 = Render(activity!!)

            render3.setDuration(500)

            render3.setAnimation(Bounce().InLeft(ll_summary))
            render3.start()
        }, 400)

        Handler().postDelayed({
            val render4 = Render(activity!!)

            render4.setDuration(500)

            render4.setAnimation(Fade().In(ll_total))
            render4.start()
        }, 500)

        ViewCompat.setNestedScrollingEnabled(rv_news, false)
    }

    private fun buttonWhat() {
        val intent = Intent(activity, WhatActivity::class.java)
        startActivity(intent)
        AcTrans.Builder(context!!).performFade()
    }

    private fun buttonCountry() {
        val intent = Intent(activity, ChooseActivity::class.java)
        startActivity(intent)
        AcTrans.Builder(context!!).performFade()
    }

    private fun tampilSummary(test: List<NewCountryItem>) {
        tampilToast(context!!, test[0].country)
    }
}
