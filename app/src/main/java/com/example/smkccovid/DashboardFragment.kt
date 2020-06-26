package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.activity.ChooseActivity
import com.example.smkccovid.activity.DetailActivity
import com.example.smkccovid.activity.MainActivity
import com.example.smkccovid.activity.WhatActivity
import com.example.smkccovid.adapter.NewsAdapter
import com.example.smkccovid.data.*
import com.example.smkccovid.model.CountryDataModel
import com.example.smkccovid.model.SelectedCountryModel
import com.example.smkccovid.viewmodel.DashboardViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import data.CovidService
import data.apiRequest
import data.httpClient
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Response
import util.*
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var listCountry : List<NewCountryItem>
    var countryData: MutableList<CountryDataModel> = ArrayList()
    private val viewModel by viewModels<DashboardViewModel>()

//    private fun simulateNews() {
//        var listTeman = ArrayList<Country>()
//        for (i in 0..10) {
//            listTeman.add(Country("1", "2"))
//        }
//        rv_news.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rv_news.adapter = NewsAdapter(requireActivity(), listTeman)
//    }

    private fun callApiGetData(sharedPreferences: SharedPreferences) {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
        val call = apiRequest.getGlobal()
        val slug = sharedPreferences.getString("slug", "indonesia")!!

//        if (FirebaseAuth.getInstance().currentUser != null && sharedPreferences.getInt("reload", 1) == 0) {
//            val edit = sharedPreferences.edit()
//            edit.putInt("reload", 1)
//            edit.apply()
//            goTo(requireContext(), MainActivity(), true, null)
//        }

        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
                tampilToast(requireContext(), "Gagal")
                callApiGetData(sharedPreferences)
            }

            override fun onResponse(
                call: Call<Summary>, response:
                Response<Summary>
            ) {
                when {
                    response.isSuccessful -> setData(
                        response.body()!!.globalSummary,
                        response.body()!!.countries,
                        slug.toLowerCase(),
                        sharedPreferences
                    )
                    else -> {
                        tampilToast(requireContext(), response.message())
                        callApiGetData(sharedPreferences)
                    }
                }
            }
        })
    }

    private fun callApiGetNews(sharedPreferences: SharedPreferences) {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.NEWSAPI_URL)
        var call = apiRequest.getNewsFromCountry(getCountry(requireContext()), AppConstants.API_KEY, "covid", 20)

        if (getCountry(requireContext()) == "us") {
            call = apiRequest.getNewsFromSource(AppConstants.NEWSAPI_SOURCES, AppConstants.API_KEY, "covid", 20)
        }

        call.enqueue(object : retrofit2.Callback<NewsParent> {
            override fun onFailure(call: Call<NewsParent>, t: Throwable) {
                tampilToast(requireContext(), t.message!!)
            }
            override fun onResponse(call: Call<NewsParent>, response:
            Response<NewsParent>
            ) {
                when {
                    response.isSuccessful -> setNews(response.body()!!.articles)
                    else -> {
                        tampilToast(requireContext(), response.message())
                        callApiGetNews(sharedPreferences)
                    }
                }
            }
        })
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
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    private fun initView() {
        setDate()
        setOnClick()
        disableTouch(requireActivity(), R.id.fl_load_main, R.id.bottomNavMain, true)

        val sharedPreferences = requireContext().getSharedPreferences("test", 0)

        val edit = sharedPreferences.edit()
        edit.putInt("loaded", 0)
        edit.apply()

        val update: Boolean = sharedPreferences.getBoolean("update", true)
        callApiGetData(sharedPreferences)
        callApiGetNews(sharedPreferences)
    }

    private fun setOnClick() {
        bt_sync.setOnClickListener { buttonSync() }
        bt_what.setOnClickListener { buttonWhat() }
        bt_edit.setOnClickListener { buttonCountry() }

        ll_blue1.setOnClickListener { goToWithBoolean(requireContext(),true, 0) }
        ll_blue2.setOnClickListener { goToWithBoolean(requireContext(),false, 0) }

        ll_green1.setOnClickListener { goToWithBoolean(requireContext(),true, 1) }
        ll_green2.setOnClickListener { goToWithBoolean(requireContext(),false, 1) }

        ll_red1.setOnClickListener { goToWithBoolean(requireContext(),true, 2) }
        ll_red2.setOnClickListener { goToWithBoolean(requireContext(),false, 2) }
    }

    fun changelanguage(context: Context) {
        //String lang = "hi_IN";
        //  Locale locale = new Locale(lang);
        val locale = Locale("in")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.configuration.setLocale(locale)
    }

    private fun setDate() {
        tv_date_dashboard.text = getDate(requireContext(), Locale.getDefault().country , null)
        tv_time_dashboard.text = getTimeNow()

        if (FirebaseAuth.getInstance().currentUser == null) {
            tv_greetings_dashboard.text = getGreetings(requireContext())
        }
        else {
            val name = FirebaseAuth.getInstance().currentUser!!.displayName!!.split(" ")[0]
            tv_greetings_dashboard.text = getGreetings(requireContext()) + ", " + name
        }
    }

    private fun setData(global: WorldWeeklyItem, countries: List<CountrySummary>, slug: String, sharedPreferences: SharedPreferences) {
        enableTouch(requireActivity(), R.id.fl_load_main, R.id.bottomNavMain, true)

        viewModel.init(requireContext());

        val countries2 = mutableListOf<CountryDataModel>()
        for (item in countries) {
            val data = CountryDataModel(item.country, item.countryCode, item.date, item.newConfirmed, item.newDeaths, item.newRecovered, item.slug, item.totalConfirmed, item.totalDeaths, item.totalRecovered)
            countries2.add(data)
            viewModel.insert(data)
        }
        Log.d("TAG", "All Data Updated: " + countries2.size)
//        viewModel.insertAll(countries2)
        Log.d("TAG", "All Data Updated: " + viewModel.allCountryDatas.value)

        val country = countries.find { it.slug.contains(slug) }!!

        Log.d("TAG", "Country: " + country)

        val edit = sharedPreferences.edit()
        edit.putInt("confirmed", global.totalConfirmed)
        edit.putBoolean("update", false)
        edit.apply()

        tv_country_dashboard.text = country.country.capitalize()

        num_blue.text = prettyCount(requireContext(), global.totalConfirmed)
        num_green.text = prettyCount(requireContext(), global.totalRecovered)
        num_red.text = prettyCount(requireContext(), global.totalDeaths)

        num_blue2.text = "+" + global.newConfirmed
        num_green2.text = "+" + global.newRecovered
        num_red2.text = "+" + global.newDeaths

        num_blue3.text = prettyCount(requireContext(), country.totalConfirmed)
        num_green3.text = prettyCount(requireContext(), country.totalRecovered)
        num_red3.text = prettyCount(requireContext(), country.totalDeaths)

        num_blue4.text = "+" + country.newConfirmed
        num_green4.text = "+" + country.newRecovered
        num_red4.text = "+" + country.newDeaths

        num_blue.invalidate()
        num_blue2.invalidate()
        num_blue3.invalidate()
        num_blue4.invalidate()

        num_green.invalidate()
        num_green2.invalidate()
        num_green3.invalidate()
        num_green4.invalidate()

        num_red.invalidate()
        num_red2.invalidate()
        num_red3.invalidate()
        num_red4.invalidate()

        tv_country_dashboard.invalidate()

//        tampilToast(requireContext(), country.totalConfirmed.toString())
    }

    fun setNews(listCountry: List<NewsItem>) {
        val list = ArrayList(listCountry.sortedBy { it.publishedAt })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        rv_news.layoutManager = layoutManager
        rv_news.adapter = NewsAdapter(requireContext(), list)
        dismissLoading(requireContext(), sp_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun buttonSync() {
        bt_sync.animate().rotation(bt_sync.rotation-360).start()
        Handler().postDelayed({
            goTo(requireContext(), MainActivity(), true)
        }, 300)
//
//        val render = Render(requireActivity())
//
//        render.setDuration(500)
//
//        render.setAnimation(Attention().Wobble(bt_sync))
//        render.start()
//
//        val render1 = Render(requireActivity())
//
//        render1.setDuration(300)
//
//        render1.setAnimation(Fade().OutLeft(ll_summary))
//        render1.start()
//
//        val render2 = Render(requireActivity())
//
//        render2.setDuration(400)
//
//        render2.setAnimation(Fade().Out(ll_total))
//        render2.start()
//
//        Handler().postDelayed({
//            val render3 = Render(requireActivity())
//
//            render3.setDuration(500)
//
//            render3.setAnimation(Bounce().InLeft(ll_summary))
//            render3.start()
//        }, 400)
//
//        Handler().postDelayed({
//            val render4 = Render(requireActivity())
//
//            render4.setDuration(500)
//
//            render4.setAnimation(Fade().In(ll_total))
//            render4.start()
//        }, 500)
//
//        ViewCompat.setNestedScrollingEnabled(rv_news, false)
    }

    private fun buttonWhat() {
        val intent = Intent(activity, WhatActivity::class.java)
        startActivity(intent)
        AcTrans.Builder(requireContext()).performFade()
    }

    private fun buttonCountry() {
        val intent = Intent(activity, ChooseActivity::class.java)
        startActivity(intent)
        AcTrans.Builder(requireContext()).performFade()
    }

    private fun tampilSummary(test: List<NewCountryItem>) {
        tampilToast(requireContext(), test[0].country)
    }

}
