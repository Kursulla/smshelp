package com.eutechpro.smshelp.sms

import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.NotificationCompat
import com.eutechpro.smshelp.R


class SmsNotificationsManager {
    private val NOTIFICATION_ID = 12343

    fun showNotification(context: Context) {
        val notificationBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content_text))

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}