package com.example.smkccovid.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smkccovid.R
import com.example.smkccovid.adapter.DetailPagerAdapter
import com.example.smkccovid.data.AppConstants
import com.example.smkccovid.data.CountryTotal
import com.example.smkccovid.data.WorldWeeklyItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.ch_detail1
import kotlinx.android.synthetic.main.activity_detail.rl_detail1
import kotlinx.android.synthetic.main.activity_detail.sk_detail1
import kotlinx.android.synthetic.main.activity_detail.tv_detail_sub1
import kotlinx.android.synthetic.main.activity_detail.tv_detail_title1
import kotlinx.android.synthetic.main.activity_detail.tv_detail_total1
import render.animations.Render
import render.animations.Zoom
import retrofit2.Call
import retrofit2.Response
import util.getDate
import util.getTimeNow
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var detailPagerAdapter: DetailPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
    }

    fun initView() {
        callApiGetStats()

        bt_close_detail.setOnClickListener { buttonBack() }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun callApiGetStats() {
        val type = this.getSharedPreferences("test", 0).getBoolean("global", true)
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)

        if (type) {
            val call2 = apiRequest.getWeeklyWorld()
            call2.enqueue(object : retrofit2.Callback<List<WorldWeeklyItem>> {
                override fun onFailure(call: Call<List<WorldWeeklyItem>>, t: Throwable) {
//                tampilToast(this, "Gagal")
                }

                override fun onResponse(
                    call: Call<List<WorldWeeklyItem>>, response: Response<List<WorldWeeklyItem>>
                ) {
                    when {
                        response.isSuccessful -> setLayout1(response.body()!!)
                        else -> {
//                        tampilToast(this, "Gagal")
                        }
                    }
                }
            })
        }
        else {
            val call2 = apiRequest.getCountry(getSharedPreferences("test", 0).getString("slug", "indonesia")!!)
            call2.enqueue(object : retrofit2.Callback<List<CountryTotal>> {
                override fun onFailure(call: Call<List<CountryTotal>>, t: Throwable) {
//                tampilToast(this, "Gagal")
                }

                override fun onResponse(
                    call: Call<List<CountryTotal>>, response: Response<List<CountryTotal>>
                ) {
                    when {
                        response.isSuccessful -> setLayout2(response.body()!!)
                        else -> {
//                        tampilToast(this, "Gagal")
                        }
                    }
                }
            })
        }
    }

    fun setLayout1(listCountry: List<WorldWeeklyItem>) {
        val sorted = listCountry.sortedByDescending { it.totalConfirmed }

        var list: MutableList<Float> = ArrayList()

        for (item in sorted) {
            if (item.totalConfirmed <= getSharedPreferences("test", 0).getInt("confirmed", 4000000)) {
                when (getBundle()) {
                    0 -> list.add(item.totalConfirmed.toFloat())
                    1 -> list.add(item.totalRecovered.toFloat())
                    2 -> list.add(item.totalDeaths.toFloat())
                }
            }
        }

        list.sortDescending()

        val last = list[0].toInt()
        val sub = last - list[1].toInt()

        list.reverse()

        when (getBundle()) {
            0 -> setData(ch_detail1, list, getString(R.string.cases))
            1 -> setData(ch_detail1, list, getString(R.string.recovered))
            2 -> setData(ch_detail1, list, getString(R.string.deaths))
        }

        when (getBundle()) {
            0 -> setText(last, sub, 0)
            1 -> setText(last, sub, 1)
            2 -> setText(last, sub, 2)
        }

        dismissLoading()
    }

    fun setLayout2(listCountry: List<CountryTotal>) {
        val sorted = listCountry.sortedByDescending { it.date }

        var list: MutableList<Float> = ArrayList()
        var j = 0
//        var i = sorted.size-j
        for (item in sorted) {
//            if (j < 7) {
//                j++
                when (getBundle()) {
                    0 -> list.add(item.confirmed.toFloat())
                    1 -> list.add(item.recovered.toFloat())
                    2 -> list.add(item.deaths.toFloat())
                }
//            }
        }

        val last = list[0].toInt()
        val sub = last - list[1].toInt()

        list = list.asReversed()

        when (getBundle()) {
            0 -> setData(ch_detail1, list, getString(R.string.cases))
            1 -> setData(ch_detail1, list, getString(R.string.recovered))
            2 -> setData(ch_detail1, list, getString(R.string.deaths))
        }

        when (getBundle()) {
            0 -> setText(last, sub, 0)
            1 -> setText(last, sub, 1)
            2 -> setText(last, sub, 2)
        }

        dismissLoading()
    }

    fun dismissLoading() {
        val render = Render(this)

        render.setDuration(1300)

        render.setAnimation(Zoom().Out(sk_detail1))
        render.start()
    }

    fun setText(total: Int, sub: Int, type: Int) {
        var string = ""
        when (type) {
            0 -> rl_detail1.setBackgroundResource(R.drawable.gradient_orange)
            1 -> rl_detail1.setBackgroundResource(R.drawable.gradient_green)
            2 -> rl_detail1.setBackgroundResource(R.drawable.gradient_red)
        }

        when (type) {
            0 -> string = getString(R.string.cases)
            1 -> string = getString(R.string.recovered)
            2 -> string = getString(R.string.deaths)
        }

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")

        val dec = java.text.DecimalFormat("#,###")

        val sharedPreferences = getSharedPreferences("test", 0)
        if (!sharedPreferences.getBoolean("global", true)) {
            val title = sharedPreferences.getString("slug", "indonesia")
            title!!.replace("-", " ")

            tv_detail_title1.text = title.capitalize()
        } else { tv_detail_title1.text = getString(R.string.global) }

        tv_detail_total1.text = dec.format(total) + " " + string
        tv_detail_sub1.text = "+" + dec.format(sub)
        tv_date_detail1.text = getDate(this, "id", null) + " " + getTimeNow()
    }

    fun setData(chart: LineChart, data: MutableList<Float>, label: String) {
        val entries: ArrayList<Entry> = ArrayList()
        var i = 0F
        for (item in data) {
            entries.add(Entry(i, item))
            i++
        }

        val color = Color.parseColor("#FFFFFF")

        val lineSet = LineDataSet(entries, label)
        lineSet.color = color
//        lineSet.setCircleColor(ContextCompat.getColor(context!!, R.color.white))
        lineSet.setDrawCircles(false)
        lineSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineSet.fillColor = color
        lineSet.setDrawFilled(true)
        lineSet.fillAlpha = 255
        lineSet.valueTextColor = ContextCompat.getColor(this,
            R.color.transparent
        )
        lineSet.lineWidth = 5F

        val description = Description()
        description.text = ""

        val lineData = LineData(lineSet)

        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.setDrawAxisLine(true)
        chart.axisLeft.axisLineWidth = 2f
        chart.axisLeft.axisLineColor = color

        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawLabels(false)

        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(true)
        chart.xAxis.setDrawLabels(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.axisLineWidth = 2f
        chart.xAxis.axisLineColor = color

        chart.setDrawBorders(false)
        chart.setDrawGridBackground(false)

        chart.setTouchEnabled(false)
        chart.axisLeft.textColor = ContextCompat.getColor(this,
            R.color.white
        )
        chart.description = description
        chart.data = lineData
        chart.legend.isEnabled = false
        chart.invalidate()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
//        initView()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        initView()
//    }

//    fun initView() {
//        detailPagerAdapter = DetailPagerAdapter(supportFragmentManager, this)
//        vp_detail.adapter = detailPagerAdapter
//        Handler().postDelayed({vp_detail.setCurrentItem(getBundle(), false)}, 75)
//        wdi_detail.setViewPager(vp_detail)
//    }
//
    private fun getBundle(): Int {
        val bundle = intent.extras
        return bundle!!.getInt("id")
    }

//    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    tools:context=".DetailActivity"
//    android:background="#00000000"
//    android:clipToPadding="false">
//
//    <androidx.viewpager.widget.ViewPager
//    android:id="@+id/vp_detail"
//    android:layout_width="match_parent"
//    android:layout_height="440dp"
//    android:layout_marginBottom="20dp"
//    android:layout_centerInParent="true"/>
//    <androidx.cardview.widget.CardView
//    android:id="@+id/cv_detail"
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    android:layout_centerHorizontal="true"
//    android:clipChildren="true"
//    android:clipToPadding="true"
//    android:layout_below="@id/vp_detail"
//    app:cardBackgroundColor="@color/transparent">
//    <com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
//    android:id="@+id/wdi_detail"
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    app:dotsColor="@color/white"
//    app:dotsStrokeColor="@color/white"
//    app:dotsCornerRadius="8dp"
//    app:dotsSize="12dp"
//    app:dotsSpacing="6dp"
//    app:dotsStrokeWidth="1dp"
//    />
//    </androidx.cardview.widget.CardView>
//
//    </RelativeLayout>
}
