package util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

fun setLocale(context: Context, lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.configuration.setLocale(locale)
}

fun loadLocale(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("test", Activity.MODE_PRIVATE)
    val lang = prefs.getString("lang", "")
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

fun getCountry(context: Context): String {
    val language = context.getSharedPreferences("test", 0).getString("lang", "en")
    return when (language) {
        "in" -> "id"
        "fil" -> "ph"
        else -> "us"
    }
}

//fun setLanguage(context: Context, language: String): ContextWrapper {
//    var mContext = context
//
//    val localeLang = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//    val locale: Locale
//    if (localeLang.size > 1)
//        locale = Locale(localeLang[0], localeLang[1])
//    else
//        locale = Locale(localeLang[0])
//
//    val res = mContext.resources
//    val configuration = res.configuration
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        val localeList = LocaleList(locale)
//        LocaleList.setDefault(localeList)
//        configuration.setLocales(localeList)
//        mContext = mContext.createConfigurationContext(configuration)
//    }
//    else {
//        configuration.setLocale(locale)
//        mContext = mContext.createConfigurationContext(configuration)
//    }
//
//    return ContextWrapper(mContext)
//}