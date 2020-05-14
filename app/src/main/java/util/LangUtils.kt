package util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*


fun setLocale(context: Context, lang: String) {
    val res = context.resources
    val dm = res.displayMetrics
    val conf = res.configuration
    conf.setLocale(Locale(lang.toLowerCase()))
    res.updateConfiguration(conf, dm)
    val editor: SharedPreferences.Editor = context.getSharedPreferences("Test", Context.MODE_PRIVATE).edit()
    editor.putString("lang", lang)
    editor.apply()
}

fun loadLocale(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("Test", Activity.MODE_PRIVATE)
    val language = prefs.getString("lang", "")
    setLocale(context, language!!)
}