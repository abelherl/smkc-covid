package com.example.smkccovid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smkccovid.adapter.RankAdapter
import com.example.smkccovid.data.AppConstants
import com.example.smkccovid.data.CountrySummary
import com.example.smkccovid.data.Summary
import data.CovidService
import data.apiRequest
import data.httpClient
import kotlinx.android.synthetic.main.fragment_rank1.*
import kotlinx.android.synthetic.main.fragment_rank2.*
import retrofit2.Call
import retrofit2.Response
import util.disableTouch
import util.dismissLoading
import util.enableTouch
import util.tampilToast


class RankFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rank2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initView() {
        callApiGetData()
    }

    private fun callApiGetData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
        val call = apiRequest.getGlobal()

        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
//                goTo(requireContext(), SignalActivity(), false)
                dismissLoading(requireContext(), sk_rank2)
                callApiGetData()
//                tampilToast(requireContext(), "Gagal")
            }
            override fun onResponse(call: Call<Summary>, response:
            Response<Summary>
            ) {
                when {
                    response.isSuccessful -> setData(response.body()!!.countries, 1)
                    else -> {
//                        goTo(requireContext(), SignalActivity(), false)
                        dismissLoading(requireContext(), sk_rank2)
                        callApiGetData()
//                        tampilToast(requireContext(), "Gagal")
                    }
                }
            }
        })
    }

     fun setData(list: List<CountrySummary>, type: Int) {
        setAdapter(list.sortedByDescending { it.totalRecovered }, type)
        dismissLoading(requireContext(), sk_rank2)

         val pref = requireContext().getSharedPreferences("test", 0)
         val edit = pref.edit()
         edit.putInt("loaded", pref.getInt("loaded", 0) + 1)
         edit.apply()

         enableTouch(requireActivity())
    }

    private fun setAdapter(list: List<CountrySummary>, type: Int) {
        Log.d("PAGER", type.toString())
        rv_rank2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_rank2.adapter = RankAdapter(requireActivity(), list, type)
    }
}