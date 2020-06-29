package com.example.smkccovid.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.smkccovid.R
import com.example.smkccovid.data.MapsCountry
import com.example.smkccovid.viewmodel.DashboardViewModel
import com.example.smkccovid.viewmodel.SettingsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.opencsv.CSVReader
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initView()
    }

    override fun onBackPressed() {
        buttonClose()
    }

    private fun initView() {
        viewModel.init(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bt_back_maps.setBackgroundColor(Color.TRANSPARENT)
        bt_search_maps.setBackgroundColor(Color.TRANSPARENT)

        bt_back_maps.setOnClickListener { buttonClose() }
        bt_search_maps.setOnClickListener { buttonSearch() }
    }

    private fun buttonClose() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun buttonSearch() {
        //
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val inputStream = InputStreamReader(assets.open("countries.csv"))

        val reader = BufferedReader(inputStream)

        val countries = reader.readLines()
        var i = 0

        for (country in countries) {
            if (i != 0) {
                val item = country.split(",")
                if (item[0] != "UM") {
                    Log.d("TAG", "reader: " + item[0])
                    val mapsCountry = MapsCountry(item[0], item[1].toDouble(), item[2].toDouble(), item[3])
                    val dataCountry = viewModel.allCountryDatas.value!!.find { it.countryCode == mapsCountry.id }!!
                    val marker = LatLng(mapsCountry.lat, mapsCountry.lon)

//                    mMap.addMarker(
//                        MarkerOptions().position(marker).title(mapsCountry.country.capitalize()).snippet(
//                            getString(R.string.cases) + ": " + dataCountry.totalConfirmed + "\n"
//                                + getString(R.string.recovered) + ": " + dataCountry.totalRecovered + "\n"
//                                + getString(R.string.deaths) + ": " + dataCountry.totalDeaths)
//                    )

                    Log.d("TAG", "reader: " + mapsCountry.country)
                }
            }
            i++
        }
//        for (reader.readLine()) {
//
//        }

        try {
            val reader = CSVReader(FileReader(resources.getResourceName(R.raw.countries)), '\t')
            Log.d("TAG", "reader: " + reader.readAll()[0])

            val countries = reader.readAll()

            for (country in countries) {
                val marker = LatLng(country[1].toDouble(), country[2].toDouble())
                mMap.addMarker(MarkerOptions().position(marker).title(country[3].capitalize()))
            }
        } catch (e: IOException) {
            Log.d("TAG", "reader: " + e.message)
        }
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
