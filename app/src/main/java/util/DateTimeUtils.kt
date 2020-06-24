package util

import android.content.Context
import com.example.smkccovid.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun getDate(context: Context, lang: String, date: String?): String {
    var cal = Calendar.getInstance()
    if (date.isNullOrEmpty()) { cal.time = Date() }
    else {
        val newDate = date.split("T")[0]
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        cal.time = sdf.parse(newDate)!!
    }
    val monthDate = cal[Calendar.MONTH]
    var monthName = ""

    when (monthDate) {
        0 -> monthName = context.resources.getString(R.string.january)
        1 -> monthName = context.resources.getString(R.string.february)
        2 -> monthName = context.resources.getString(R.string.march)
        3 -> monthName = context.resources.getString(R.string.april)
        4 -> monthName = context.resources.getString(R.string.may)
        5 -> monthName = context.resources.getString(R.string.june)
        6 -> monthName = context.resources.getString(R.string.july)
        7 -> monthName = context.resources.getString(R.string.august)
        8 -> monthName = context.resources.getString(R.string.september)
        9 -> monthName = context.resources.getString(R.string.october)
        10 -> monthName = context.resources.getString(R.string.november)
        else -> monthName = context.resources.getString(R.string.december)
    }

    return when (lang) {
        "en" -> monthName + " " + cal[Calendar.DAY_OF_MONTH].toString() + " " + cal[Calendar.YEAR]
        else -> cal[Calendar.DAY_OF_MONTH].toString() + " " + monthName + " " + cal[Calendar.YEAR]
    }
}

fun getTimeNow(): String {
    val cal = Calendar.getInstance()
    cal.time = Date()
    var hour = cal[Calendar.HOUR].toString()
    var minute = cal[Calendar.MINUTE].toString()
    val ampm = when (cal[Calendar.AM_PM]) {
        0 -> "AM"
        else -> "PM"
    }
    if (minute.toCharArray().size == 1) { minute = "0" + minute }
    if (hour == "0") { hour = "12" }
    return  hour + ":" + minute + " " + ampm
}

fun getGreetings(context: Context): String {
    val cal = Calendar.getInstance()
    cal.time = Date()
    val hour = cal[Calendar.HOUR]
    val ampm = cal[Calendar.AM_PM]

    if (ampm == 0) {
        if (hour >= 11) {
            return context.resources.getString(R.string.afternoon)
        } else {
            return context.resources.getString(R.string.morning)
        }
    } else {
        if (hour <= 5) {
            return context.resources.getString(R.string.afternoon)
        } else {
            return context.resources.getString(R.string.evening)
        }
    }
}


fun prettyCount(context: Context, number: Number): String? {
    val sharedPref = context.getSharedPreferences("test", 0)
    val lang = sharedPref.getString("lang", "en")

    var suffix = arrayOf("", "K", "M", "B")

    if (lang == "in") {
        suffix = arrayOf("", "rb", "jt", "m")
    }

    val numValue: Long = number.toLong()
    val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(
            numValue / Math.pow(
                10.0,
                base * 3.toDouble()
            )
        ) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(numValue)
    }
}