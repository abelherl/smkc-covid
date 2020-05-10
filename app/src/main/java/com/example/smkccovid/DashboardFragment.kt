package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import render.animations.*


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
        bt_sync.animate().rotation(bt_sync.rotation-360).start()

        val render1 = Render(activity!!)

        render1.setDuration(300)

        render1.setAnimation(Fade().OutDown(ll_summary))
        render1.start()

        Handler().postDelayed({
            val render2 = Render(activity!!)

            render2.setDuration(500)

            render2.setAnimation(Bounce().InDown(ll_summary))
            render2.start()
        }, 400)

        ViewCompat.setNestedScrollingEnabled(rv_news, false)
    }
}
