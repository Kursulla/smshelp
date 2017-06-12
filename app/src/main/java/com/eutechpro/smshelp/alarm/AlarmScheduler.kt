package com.eutechpro.smshelp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.util.*

/**
 * Used to schedule and un schedule sending of SMS at certain date.
 */
class AlarmScheduler(val context: Context):AnkoLogger {
    private val FREQUENCY_INTERVAL = 1000 * 10L

    fun scheduleNextAlarm(dateToFireAlarm: Date, alarmId: Int) {
        debug("Schedule Next Alarm for " + dateToFireAlarm.toString())

        referenceToAlarmManager().setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                dateToFireAlarm.time,
                FREQUENCY_INTERVAL,
                pendingIntentToFireOnAlarmEvent(alarmId))
    }

    fun unScheduleNextAlarm(alarmId: Int) {
        debug("Un Schedule Next Alarm")
        referenceToAlarmManager().cancel(pendingIntentToFireOnAlarmEvent(alarmId))
    }

    private fun referenceToAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private fun pendingIntentToFireOnAlarmEvent(alarmId: Int) = PendingIntent.getBroadcast(context, alarmId, Intent(context, AlarmServiceReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
}