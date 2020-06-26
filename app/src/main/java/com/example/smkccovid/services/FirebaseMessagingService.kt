package com.example.smkccovid.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.smkccovid.R
import com.example.smkccovid.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
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

            Log.d("TAG", "Message title: " + notification.title)
            Log.d("TAG", "Message body: " + notification.body)

            sendNotification(title!!, body!!)
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
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
            .setSound(defaultSoundUri)
            .setVibrate(vibration)
            .setContentText(body)
            .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        val unique = (Date().time / 1000L % Int.MAX_VALUE).toInt()

        getManager()!!.notify(unique, notification)
    }

    private fun getManager(): NotificationManager? {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}