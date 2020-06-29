package com.example.smkccovid.activity

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.smkccovid.R
import com.example.smkccovid.data.Country
import com.example.smkccovid.data.CountrySummary
import com.example.smkccovid.data.CountryTotal
import com.example.smkccovid.data.NewCountryItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_web_view.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initView()
    }

    override fun onBackPressed() {
        buttonClose()
    }

    private fun initView() {
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
        //aw
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val countries = listOf<CountryTotal>()

        for (country in countries) {
            val marker = LatLng(country.lat.toDouble(), country.lon.toDouble())
            mMap.addMarker(MarkerOptions().position(marker).title(country.country.capitalize()))
        }
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
