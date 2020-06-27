package com.example.smkccovid.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.smkccovid.R
import com.example.smkccovid.activity.MainActivity
import com.example.smkccovid.data.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import data.CovidService
import data.apiRequest
import data.httpClient
import retrofit2.Call
import retrofit2.Response
import java.util.*


class FirebaseMessagingService() : FirebaseMessagingService() {
//    private final TAG = "Test"

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        if (p0.data.isNotEmpty()) {
            Log.d("TAG", "Message data payload: " + p0.data)
        }

        val isNotificationOn = this.getSharedPreferences("test", 0).getBoolean("notification", true)

        if (p0.notification != null && isNotificationOn) {
            val notification = p0.notification!!

            val title = notification.title
            val body = notification.body
            val id = notification.channelId
            val sharedPreferences = this.getSharedPreferences("test", 0)
//            var bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

//            try {
//                val url = URL(notification.icon)
//                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            } catch (e: IOException) {
//                println(e)
//            }

            Log.d("TAG", "Message title: " + notification.title)
            Log.d("TAG", "Message body: " + notification.body)

            if (sharedPreferences.getBoolean("notification", true)) {
                when (id) {
                    "GLOBAL" -> globalNotification(title!!, sharedPreferences)
                    "COUNTRY" -> countryNotification(title!!, sharedPreferences)
                }
            }
            else {
                sendNotification(title!!, body!!)
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    private fun countryNotification(title: String, sharedPreferences: SharedPreferences) {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
        val call = apiRequest.getGlobal()
        val slug = sharedPreferences.getString("slug", "indonesia")!!.toLowerCase()

//        if (FirebaseAuth.getInstance().currentUser != null && sharedPreferences.getInt("reload", 1) == 0) {
//            val edit = sharedPreferences.edit()
//            edit.putInt("reload", 1)
//            edit.apply()
//            goTo(requireContext(), MainActivity(), true, null)
//        }

        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
//                tampilToast(this, "Gagal")
                countryNotification(title, sharedPreferences)
            }

            override fun onResponse(
                call: Call<Summary>, response:
                Response<Summary>
            ) {
                when {
                    response.isSuccessful -> setCountryData(title, response.body()!!.countries, slug, sharedPreferences)
                    else -> {
//                        tampilToast(this@FirebaseMessagingService, response.message())
                        countryNotification(title, sharedPreferences)
                    }
                }
            }
        })
    }

    private fun globalNotification(title: String, sharedPreferences: SharedPreferences) {
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVID_URL)
        val call = apiRequest.getGlobal()

//        if (FirebaseAuth.getInstance().currentUser != null && sharedPreferences.getInt("reload", 1) == 0) {
//            val edit = sharedPreferences.edit()
//            edit.putInt("reload", 1)
//            edit.apply()
//            goTo(requireContext(), MainActivity(), true, null)
//        }

        call.enqueue(object : retrofit2.Callback<Summary> {
            override fun onFailure(call: Call<Summary>, t: Throwable) {
//                tampilToast(this, "Gagal")
                globalNotification(title, sharedPreferences)
            }

            override fun onResponse(
                call: Call<Summary>, response:
                Response<Summary>
            ) {
                when {
                    response.isSuccessful -> setGlobalData(title, response.body()!!.globalSummary, sharedPreferences)
                    else -> {
//                        tampilToast(this@FirebaseMessagingService, response.message())
                        globalNotification(title, sharedPreferences)
                    }
                }
            }
        })
    }

    private fun setCountryData(title: String, countries: List<CountrySummary>, slug: String, sharedPreferences: SharedPreferences) {
        if (sharedPreferences.getBoolean("country", true)) {
            val country = countries.find { it.slug == slug }!!
            val newTitle = title.replace("Country", country.country.capitalize())
            val body = getString(R.string.cases) + ": " + "+" + country.newConfirmed + "\n" + getString(R.string.recovered) + ": " + "+" + country.newRecovered + "\n" + getString(R.string.deaths) + ": " + "+" + country.newDeaths
            sendNotification(newTitle, body)
        }
    }

    private fun setGlobalData(title: String, globalSummary: WorldWeeklyItem, sharedPreferences: SharedPreferences) {
        if (sharedPreferences.getBoolean("global", true)) {
            val body = getString(R.string.cases) + ": " + "+" + globalSummary.newConfirmed + "\n" + getString(R.string.recovered) + ": " + "+" + globalSummary.newRecovered + "\n" + getString(R.string.deaths) + ": " + "+" + globalSummary.newDeaths
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val vibration = longArrayOf(500,500,200,500,500)
        val NOTIFICATION_CHANNEL = "Pangelyn"
        val builder = NotificationCompat.Builder(this)
        val notification: Notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val iosChannel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                "Pangelyn", NotificationManager.IMPORTANCE_DEFAULT
            )
            iosChannel.enableLights(true)
            iosChannel.enableVibration(true)
            iosChannel.lightColor = Color.GREEN
            iosChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            iosChannel.vibrationPattern = vibration
            getManager()!!.createNotificationChannel(iosChannel)
            builder.setChannelId(NOTIFICATION_CHANNEL)
        }

        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setChannelId(NOTIFICATION_CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher)
//            .setLargeIcon(bitmap)
            .setSound(defaultSoundUri)
            .setVibrate(vibration)
            .setContentText("Daily Update")
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        val unique = (Date().time / 1000L % Int.MAX_VALUE).toInt()

        getManager()!!.notify(unique, notification)
    }

    private fun getManager(): NotificationManager? {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}