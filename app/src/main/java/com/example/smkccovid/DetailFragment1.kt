package com.example.smkccovid

import android.graphics.Color
import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.smkccovid.data.AppConstants
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
import kotlinx.android.synthetic.main.fragment_detail1.*
import render.animations.Render
import render.animations.Zoom
import retrofit2.Call
import retrofit2.Response
import util.tampilToast
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailFragment1 : Fragment() {
//    val test1: MutableList = arrayOf(183F, 1298F, 4291F, 183F, 1298F, 4291F)
    var type = ""
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_detail1, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initView()
//    }
//
//    fun initView() {
//        callApiGetStats()
//    }
//
////    private fun callApiGetSummary() {
//////        showLoading(this, swipeRefreshLayout)
////        val httpClient = httpClient()
////        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
////
////        val call = apiRequest.getGlobal()
////        call.enqueue(object : retrofit2.Callback<Summary> {
////            override fun onFailure(call: Call<Summary>, t: Throwable) {
////                tampilToast(context!!, "Gagal")
////            }
////            override fun onResponse(call: Call<Summary>, response: Response<Summary>
////            ) {
////                when {
////                    response.isSuccessful -> callApiGetStats(response.body()!!)
////                    else -> {
////                        tampilToast(context!!, "Gagal")
////                    }
////                }
////            }
////        })
////    }
//
//    private fun callApiGetStats() {
//        val httpClient = httpClient()
//        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
//
//        val call2 = apiRequest.getWeeklyWorld()
//        call2.enqueue(object : retrofit2.Callback<List<WorldWeeklyItem>> {
//            override fun onFailure(call: Call<List<WorldWeeklyItem>>, t: Throwable) {
//                tampilToast(context!!, "Gagal")
//            }
//            override fun onResponse(call: Call<List<WorldWeeklyItem>>, response: Response<List<WorldWeeklyItem>>
//            ) {
//                when {
//                    response.isSuccessful -> setLayout(response.body()!!)
//                    else -> {
//                        tampilToast(context!!, "Gagal")
//                    }
//                }
//            }
//        })
//    }
//
//    fun setLayout(listCountry: List<WorldWeeklyItem>) {
//        val sorted = listCountry.sortedBy { it.totalConfirmed }
//        var list: MutableList<Float> = ArrayList()
//        var j = 0
////        var i = sorted.size-j
//        for (item in sorted) {
//            if (j<7) {
//                list.add(item.totalConfirmed.toFloat())
//                j++
//            }
//        }
//
//        val last = sorted[sorted.size - 1]
//
//        setData(ch_detail, list, getString(R.string.cases))
//        setText(last.totalConfirmed, last.newConfirmed)
//
//        dismissLoading()
//    }
//
//    fun dismissLoading() {
//        val render = Render(context!!)
//
//        render.setDuration(1300)
//
//        render.setAnimation(Zoom().Out(sk_detail))
//        render.start()
//    }
//
//    fun setText(total: Int, sub: Int) {
//        val format = NumberFormat.getCurrencyInstance()
//        format.maximumFractionDigits = 0
//        format.currency = Currency.getInstance("USD")
//
//        val dec = java.text.DecimalFormat("#,###")
//
//        tv_detail_title.text = getString(R.string.global)
//        tv_detail_total.text = dec.format(total) + getString(R.string.cases)
//        tv_detail_sub.text = "+ " + dec.format(sub)
//    }
//
//    fun setData(chart: LineChart, data: MutableList<Float>, label: String) {
//        val entries: ArrayList<Entry> = ArrayList()
//        var i = 0F
//        for (item in data) {
//            entries.add(Entry(i, item))
//            i++
//        }
//
//        val color = Color.parseColor("#FFFFFF")
//
//        val lineSet = LineDataSet(entries, label)
//        lineSet.color = color
////        lineSet.setCircleColor(ContextCompat.getColor(context!!, R.color.white))
//        lineSet.setDrawCircles(false)
//        lineSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        lineSet.fillColor = color
//        lineSet.setDrawFilled(true)
////        lineSet.fillAlpha = 255
//        lineSet.valueTextColor = ContextCompat.getColor(context!!, R.color.transparent)
//        lineSet.lineWidth = 5F
//
//        val description = Description()
//        description.text = ""
//
//        val lineData = LineData(lineSet)
//
//        chart.axisLeft.setDrawGridLines(false)
//        chart.axisLeft.setDrawAxisLine(true)
//        chart.axisLeft.axisLineWidth = 2f
//        chart.axisLeft.axisLineColor = color
//
//        chart.axisRight.setDrawGridLines(false)
//        chart.axisRight.setDrawAxisLine(false)
//        chart.axisRight.setDrawLabels(false)
//
//        chart.xAxis.setDrawGridLines(false)
//        chart.xAxis.setDrawAxisLine(true)
//        chart.xAxis.setDrawLabels(false)
//        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        chart.xAxis.axisLineWidth = 2f
//        chart.xAxis.axisLineColor = color
//
//        chart.setDrawBorders(false)
//        chart.setDrawGridBackground(false)
//
//        chart.setTouchEnabled(false)
//        chart.axisLeft.textColor = ContextCompat.getColor(context!!, R.color.white)
//        chart.description = description
//        chart.data = lineData
//        chart.legend.isEnabled = false
//        chart.invalidate()
//    }

    private fun inflate(id: String) {
        when (id) {
            "cases" -> setLayout(R.drawable.gradient_orange, R.string.cases)
            "recovered" -> setLayout(R.drawable.gradient_green, R.string.recovered)
            "deaths" -> setLayout(R.drawable.gradient_red, R.string.deaths)
        }
    }

    private fun setLayout(drawable: Int, string: Int) {
//        rl_detail.setBackgroundResource(drawable)
//        tv_detail_title.text = getString(string)
    }
}
