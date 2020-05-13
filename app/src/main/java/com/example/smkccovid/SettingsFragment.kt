package com.example.smkccovid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_settings.*
import id.voela.actrans.AcTrans

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        bt_country.setOnClickListener { buttonCountry() }
        bt_language.setOnClickListener { buttonCountry() }
//        Glide.with(this).load("https://www.countryflags.io/be/flat/32.png").into(iv_what)
    }

    private fun buttonCountry() {
        val intent = Intent(activity, ChooseActivity::class.java)
        startActivity(intent)
        AcTrans.Builder(context!!).performFade()
    }
}
