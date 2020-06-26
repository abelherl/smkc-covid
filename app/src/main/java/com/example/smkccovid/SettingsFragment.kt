package com.example.smkccovid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.smkccovid.activity.LoginActivity
import com.example.smkccovid.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.fragment_settings.*
import util.goTo
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
        bt_sign_in.setOnClickListener { buttonSignIn() }
        bt_sign_out.setOnClickListener { buttonSignOut() }
        switch1.setOnCheckedChangeListener { buttonView, isChecked -> buttonNotification(isChecked) }
        setSettings()
        setAccountInformation()
    }

    private fun buttonNotification(isChecked: Boolean) {
        val edit = context!!.getSharedPreferences("test", 0).edit()
        edit.putBoolean("notification", isChecked)
        edit.apply()
    }

    private fun buttonSignIn() {
        goTo(context!!, LoginActivity(), false, null)
    }

    private fun buttonSignOut() {
        FirebaseAuth.getInstance().signOut()
        val edit = context!!.getSharedPreferences("test", 0).edit()
        edit.putString("slug", "indonesia")
        edit.apply()
        goTo(context!!, MainActivity(), true, null)
    }

    private fun setAccountInformation() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            tv_name_settings.visibility = View.INVISIBLE
            tv_email_settings.visibility = View.GONE
            rl_sign_in.visibility = View.VISIBLE
            rl_sign_out.visibility = View.GONE
        }
        else {
            tv_name_settings.visibility = View.VISIBLE
            tv_email_settings.visibility = View.VISIBLE
            rl_sign_in.visibility = View.GONE
            rl_sign_out.visibility = View.VISIBLE

            Glide.with(context!!).load(user.photoUrl).into(iv_account_settings)
            tv_name_settings.text = user.displayName
            tv_email_settings.text = user.email
        }
    }

    private fun setSettings() {
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

        val isNotificationOn = context!!.getSharedPreferences("test", 0).getBoolean("notification", true)

        switch1.isChecked = isNotificationOn
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
