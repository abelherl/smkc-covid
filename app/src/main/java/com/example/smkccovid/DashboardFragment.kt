package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.adapter.NewsAdapter
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import render.animations.Attention
import render.animations.Bounce
import render.animations.Fade
import render.animations.Render
import util.loadLocale
import util.setLocale
import util.tampilToast
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    lateinit var listTeman : ArrayList<Country>
    lateinit var listCountry : List<NewCountryItem>

    private fun simulasiDataTeman() {
        listTeman = ArrayList()
        listTeman.add(Country("Fakhry", "1932"))
        listTeman.add(Country("Amdo", "121"))
        listTeman.add(Country("Fakhry", "1932"))
        listTeman.add(Country("Amdo", "121"))
        listTeman.add(Country("Amdo", "121"))
    }

    private fun tampilTeman() {
//        rv_main.layoutManager = LinearLayoutManager(activity)
//        rv_main.adapter = CountryAdapter(activity!!, listTeman)
        rv_news.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_news.adapter = NewsAdapter(activity!!, listTeman)
    }

    private fun configureRv() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setSharedPref()
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
        loadLocale(context!!)
        super.onConfigurationChanged(newConfig)
    }
    private fun initView() {
        simulasiDataTeman()
        tampilTeman()

        bt_sync.setOnClickListener { buttonSync() }
        bt_what.setOnClickListener { buttonWhat() }
        bt_edit.setOnClickListener { buttonCountry() }

//        swipeRefreshLayout.setOnRefreshListener { onRefresh() }

        num_blue.text = prettyCount(1811942)
        num_green.text = prettyCount(592741)
        num_red.text = prettyCount(38142)

        num_blue3.text = prettyCount(15942)
        num_green3.text = prettyCount(1741)
        num_red3.text = prettyCount(271)

//        FlingBehavior.apply { sv_main }
//        Glide.with(this).load("https://www.countryflags.io/be/flat/32.png").into(iv_what)
    }

    fun setSharedPref() {
        val test by lazy { context!!.getSharedPreferences("test", Context.MODE_PRIVATE) }
        val edit = test.edit()
        val lang = Locale.getDefault().language
        setLocale(context!!, lang)
        loadLocale(context!!)
        edit.putString("lang", lang)
        edit.apply()
    }

    fun changelanguage(context: Context) {
        //String lang = "hi_IN";
        //  Locale locale = new Locale(lang);
        val locale = Locale("id")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.configuration.setLocale(locale)
    }

//    fun onRefresh() {
//        tampilToast(context!!, "Refreshed")
//        dismissLoading(swipeRefreshLayout)
//    }

    fun prettyCount(number: Number): String? {
        val sharedPref = context!!.getSharedPreferences("test", 0)
        val lang = sharedPref.getString("lang", "en")

        var suffix = arrayOf("", "K", "M", "B")

        if (lang == "id") {
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
