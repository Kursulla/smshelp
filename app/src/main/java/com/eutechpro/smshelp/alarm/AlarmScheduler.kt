package com.eutechpro.smshelp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * Used to schedule and un schedule sending of SMS at certain date.
 */
class AlarmScheduler(val context: Context):AnkoLogger {
    private val FREQUENCY_INTERVAL = 1000 * 10L

    fun scheduleNextAlarm(sms: Sms) {
        debug("Schedule Next Alarm for " + sms.date.toString())

        referenceToAlarmManager().setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                sms.date.time,
                FREQUENCY_INTERVAL,
                pendingIntentToFireOnAlarmEvent(sms.number.toInt()))
    }

    fun unScheduleNextAlarm(alarmId: Int) {
        debug("Un Schedule Next Alarm")
        referenceToAlarmManager().cancel(pendingIntentToFireOnAlarmEvent(alarmId))
    }

    private fun referenceToAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private fun pendingIntentToFireOnAlarmEvent(alarmId: Int) = PendingIntent.getBroadcast(context, alarmId, Intent(context, AlarmServiceReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
}