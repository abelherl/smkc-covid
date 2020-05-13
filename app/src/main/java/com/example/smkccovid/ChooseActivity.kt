package com.example.smkccovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.adapter.NewCountryAdapter
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import retrofit2.Call
import retrofit2.Response
import util.dismissLoading
import util.showLoading
import util.tampilToast

class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
//        OverScrollDecoratorHelper.setUpOverScroll(sv_choose)

        callApiGetSummary()

        bt_back2.setOnClickListener { buttonBack() }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun callApiGetSummary() {
//        showLoading(this, swipeRefreshLayout)
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient)

        val call = apiRequest.getCountries()
        call.enqueue(object : retrofit2.Callback<List<NewCountryItem>> {
            override fun onFailure(call: Call<List<NewCountryItem>>, t: Throwable) {
                tampilToast(this@ChooseActivity, "Gagal")
            }
            override fun onResponse(call: Call<List<NewCountryItem>>, response:
            Response<List<NewCountryItem>>
            ) {
                when {
                    response.isSuccessful -> setRv(response.body()!!)
                    else -> {
                        tampilToast(this@ChooseActivity, "Gagal")
                    }
                }
            }
        })
    }

    fun setRv(listCountry: List<NewCountryItem>) {

        val list = listCountry.sortedBy { it.slug }

        rv_select.layoutManager = LinearLayoutManager(this)
        rv_select.adapter = NewCountryAdapter(this, list)
    }
}
