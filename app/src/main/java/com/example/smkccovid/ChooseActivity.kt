package com.example.smkccovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.adapter.NewCountryAdapter
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_choose.*
import render.animations.Fade
import render.animations.Render
import render.animations.Zoom
import retrofit2.Call
import retrofit2.Response
import util.tampilToast


class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.smkccovid.R.layout.activity_choose)
        initView()
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun initView() {
//        OverScrollDecoratorHelper.setUpOverScroll(sv_choose)
        bt_back2.setOnClickListener { buttonBack() }
        callApiGetSummary()
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

        dismissLoading()
    }

    fun dismissLoading() {
        val render = Render(this)

        render.setDuration(1300)

        render.setAnimation(Zoom().Out(spin_kit))
        render.start()
    }
}
