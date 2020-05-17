package com.example.smkccovid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.smkccovid.activity.MainActivity
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : Fragment() {
    var locale = ""
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

//    override fun onResume() {
//        super.onResume();
//        if (localeHasChanged()) {
//            changeLanguage()
//        }
//    }

    private fun initView() {
        bt_country.setOnClickListener { buttonCountry() }
        bt_language.setOnClickListener { buttonCountry() }
        setFlagImage()
    }

    private fun setFlagImage() {
        locale = Locale.getDefault().language

        if (locale == "in") {
            ib_language.setImageResource(R.drawable.indonesia)
        }
        else if (locale == "en") {
            ib_language.setImageResource(R.drawable.usa)
        }
        else {
            ib_language.setImageResource(R.drawable.philippines)
        }
    }

    private fun buttonCountry() {
        showLanguageChangeDialog()
    }

    fun localeHasChanged(): Boolean {
        return locale != Locale.getDefault().language
    }

    private fun showLanguageChangeDialog() {
        val languages = arrayOf("English", "Bahasa Indonesia", "Filipino")
        val mBuilder = AlertDialog.Builder(context!!)
        mBuilder.setTitle(getString(R.string.select_language))
        mBuilder.setSingleChoiceItems(
            languages, -1
        ) { dialog, which ->
            if (which == 1) {
                val edit = activity!!.baseContext.getSharedPreferences("test", 0).edit()
                edit.putString("lang", "in")
                edit.apply()
            } else if (which == 0) {
                val edit = activity!!.baseContext.getSharedPreferences("test", 0).edit()
                edit.putString("lang", "en")
                edit.apply()
            } else {
                val edit = activity!!.baseContext.getSharedPreferences("test", 0).edit()
                edit.putString("lang", "fil")
                edit.apply()
            }
            changeLanguage()
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun changeLanguage() {
        Handler().post {
            val intent = Intent(context, MainActivity()::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            AcTrans.Builder(context!!).performFade()
        }
    }
}
