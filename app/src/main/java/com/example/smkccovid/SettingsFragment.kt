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
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.smkccovid.activity.LoginActivity
import com.example.smkccovid.activity.MainActivity
import com.example.smkccovid.model.SettingsDataModel
import com.example.smkccovid.viewmodel.DashboardViewModel
import com.example.smkccovid.viewmodel.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.fragment_settings.*
import render.animations.Fade
import render.animations.Render
import util.goTo
import java.util.*


class SettingsFragment : Fragment() {
    var locale = ""
    private val viewModel by viewModels<SettingsViewModel>()

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
        viewModel.init(requireContext())
        bt_country.setOnClickListener { buttonCountry() }
        bt_language.setOnClickListener { buttonCountry() }
        bt_sign_in.setOnClickListener { buttonSignIn() }
        bt_sign_out.setOnClickListener { buttonSignOut() }
        switch1.setOnCheckedChangeListener { _, isChecked -> buttonNotification("notification", isChecked) }
        switch_global.setOnCheckedChangeListener { _, isChecked -> buttonNotification("global", isChecked) }
        switch_country.setOnCheckedChangeListener { _, isChecked -> buttonNotification("country", isChecked) }
        setSettings()
        setAccountInformation()
    }

    private fun buttonNotification(type: String, isChecked: Boolean) {
        val edit = requireContext().getSharedPreferences("test", 0).edit()
        edit.putBoolean(type, isChecked)
        edit.apply()

//        var settings: SettingsDataModel?
//
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            val user = FirebaseAuth.getInstance().currentUser!!
//
//            if (viewModel.allSettingsDatas.value?.find { it.id == user.uid } == null) {
//                settings = SettingsDataModel(user.uid, "en", true, true, true)
//                viewModel.insert(settings)
//            }
//            else {
//                settings = viewModel.allSettingsDatas.value?.find { it.id == user.uid }
//            }
//        }
//        else {
//            if (viewModel.allSettingsDatas.value?.find { it.id == "offline" } == null) {
//                viewModel.insert(SettingsDataModel("offline", "en", true, true, true))
//            }
//
//            settings = viewModel.allSettingsDatas.value?.find { it.id == "offline" }
//        }
//
//        when (type) {
//            "notification" -> settings!!.notification = isChecked
//            "global" -> settings!!.global = isChecked
//            "bountry" -> settings!!.country = isChecked
//        }
//
//        viewModel.update(settings!!)

        if (type == "notification") {
            val render = Render(requireActivity())
            render.setDuration(500)

            if (isChecked) {
                render.setAnimation(Fade().InDown(ll_notification))
                ll_notification.visibility = View.VISIBLE
            }
            else {
                render.setAnimation(Fade().Out(ll_notification))
                Handler().postDelayed({
                    ll_notification.visibility = View.GONE
                }, 400)
            }

            render.start()
        }
    }

    private fun buttonSignIn() {
        goTo(requireContext(), LoginActivity(), false, null)
    }

    private fun buttonSignOut() {
        FirebaseAuth.getInstance().signOut()
        val edit = requireContext().getSharedPreferences("test", 0).edit()
        edit.putString("slug", "indonesia")
        edit.apply()
        goTo(requireContext(), MainActivity(), true, null)
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

            Glide.with(requireContext()).load(user.photoUrl).into(iv_account_settings)
            tv_name_settings.text = user.displayName
            tv_email_settings.text = user.email
        }
    }

    private fun setSettings() {
//        locale = Locale.getDefault().language

//        var settings : SettingsDataModel?
//
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            val user = FirebaseAuth.getInstance().currentUser!!
//
//            if (viewModel.allSettingsDatas.value?.find { it.id == user.uid } == null) {
//                viewModel.insert(SettingsDataModel(user.uid, "en", true, true, true))
//            }
//
//            settings = viewModel.allSettingsDatas.value?.find { it.id == user.uid }
//        }
//        else {
//            if (viewModel.allSettingsDatas.value?.find { it.id == "offline" } == null) {
//                viewModel.insert(SettingsDataModel("offline", "en", true, true, true))
//            }
//
//            settings = viewModel.allSettingsDatas.value?.find { it.id == "offline" }
//        }

        val isNotificationOn = requireContext().getSharedPreferences("test", 0).getBoolean("notification", true)
        val isGlobalOn = requireContext().getSharedPreferences("test", 0).getBoolean("global", true)
        val isCountryOn = requireContext().getSharedPreferences("test", 0).getBoolean("country", true)

//        val isNotificationOn = settings.notification
//        val isGlobalOn = settings.global
//        val isCountryOn = settings.country

        if (!isNotificationOn) {
            ll_notification.visibility = View.GONE
        }
        else {
            ll_notification.visibility = View.VISIBLE
        }

        switch1.isChecked = isNotificationOn
        switch_global.isChecked = isGlobalOn
        switch_country.isChecked = isCountryOn
    }

    private fun buttonCountry() {
        showLanguageChangeDialog()
    }

    fun localeHasChanged(): Boolean {
        return locale != Locale.getDefault().language
    }

    private fun showLanguageChangeDialog() {
        val languages = arrayOf("English", "Bahasa Indonesia", "Filipino")
        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle(getString(R.string.select_language))
        mBuilder.setSingleChoiceItems(
            languages, -1
        ) { dialog, which ->
            if (which == 1) {
                val edit = requireActivity().baseContext.getSharedPreferences("test", 0).edit()
                edit.putString("lang", "in")
                edit.apply()
            } else if (which == 0) {
                val edit = requireActivity().baseContext.getSharedPreferences("test", 0).edit()
                edit.putString("lang", "en")
                edit.apply()
            } else {
                val edit = requireActivity().baseContext.getSharedPreferences("test", 0).edit()
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
            AcTrans.Builder(requireContext()).performFade()
        }
    }
}
