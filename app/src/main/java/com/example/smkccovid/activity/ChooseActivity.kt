package com.example.smkccovid.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.data.NewCountryItem
import com.example.smkccovid.R
import com.example.smkccovid.adapter.NewCountryAdapter
import com.example.smkccovid.data.AppConstants
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_choose.*
import retrofit2.Call
import retrofit2.Response
import util.dismissLoading
import util.tampilToast
import java.util.*


class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        initView()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context? {
        val language: String = context.getSharedPreferences("test", 0).getString("lang", "")!!
        val locale = Locale(language)
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context? {
        val resources: Resources = context.resources
        val configuration: Configuration = resources.getConfiguration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
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
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)

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
        rv_select.adapter = NewCountryAdapter(this, list) {
            val item = it
            tampilToast(this, item.country)
        }

        dismissLoading(this, spin_kit)
    }
}
