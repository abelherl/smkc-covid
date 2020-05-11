package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import data.CovidService
import data.apiRequest
import data.httpClient
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import render.animations.*
import retrofit2.Call
import retrofit2.Response
import util.dismissLoading
import util.showLoading
import util.tampilToast
import javax.security.auth.callback.Callback


class DashboardFragment : Fragment() {

    lateinit var listTeman : ArrayList<Country>
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
    private fun initView() {
        simulasiDataTeman()
        tampilTeman()

        bt_sync.setOnClickListener { buttonSync() }
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun buttonSync() {
        callApiGetSummary()

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

    private fun callApiGetSummary() {
        showLoading(context!!, swipeRefreshLayout)
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient)

        val call = apiRequest.getGlobal()
        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
                dismissLoading(swipeRefreshLayout)
            }
            override fun onResponse(call: Call<Summary>, response:
            Response<Summary>) {
                dismissLoading(swipeRefreshLayout)
                when {
                    response.isSuccessful -> tampilSummary(response.body()?.globalSummary.toString()!!)

                    else -> {
                        tampilToast(context!!, "Gagal")
                    }
                }
            }
        })
    }

    private fun tampilSummary(globalSummary: String) {
        tampilToast(context!!, globalSummary)
    }
}
