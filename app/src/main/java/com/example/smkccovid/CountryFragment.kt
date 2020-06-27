package com.example.smkccovid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {
    val test1 = arrayOf(183F, 1298F, 4291F, 183F, 1298F, 4291F)
    val test2 = arrayOf(54F, 294F, 791F, 54F, 294F, 791F)
    val test3 = arrayOf(4F, 88F, 142F, 4F, 88F, 142F)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initView() {
        setData(ch_country1, test1, "Confirmed", ContextCompat.getColor(requireContext(), R.color.blue))
        setData(ch_country2, test2, "Recovered", ContextCompat.getColor(requireContext(), R.color.green))
        setData(ch_country3, test3, "Deaths", ContextCompat.getColor(requireContext(), R.color.red))
    }

    fun setData(chart: LineChart, data: Array<Float>, label: String, color: Int) {
        val entries: ArrayList<Entry> = ArrayList()
        var i = 0F
        for (item in data) {
            entries.add(Entry(i, item))
            i++
        }

        val lineSet = LineDataSet(entries, label)
        lineSet.color = color
        lineSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.white))
        lineSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineSet.fillColor = color
        lineSet.setDrawFilled(true)
//        lineSet.fillAlpha = 255
        lineSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.transparent)
        lineSet.lineWidth = 5F

        val description = Description()
        description.text = ""

        val lineData = LineData(lineSet)

        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.setDrawAxisLine(true)
        chart.axisLeft.axisLineWidth = 2f

        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawLabels(false)

        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(true)
        chart.xAxis.setDrawLabels(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.axisLineWidth = 2f

        chart.setDrawBorders(false)
        chart.setDrawGridBackground(false)

        chart.setTouchEnabled(false)
        chart.axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.white)
        chart.description = description
        chart.data = lineData
        chart.legend.isEnabled = false
        chart.invalidate()
    }
}
