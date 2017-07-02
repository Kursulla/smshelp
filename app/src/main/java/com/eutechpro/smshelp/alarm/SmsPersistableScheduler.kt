package com.eutechpro.smshelp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eutechpro.smshelp.alarm.persistance.AlarmPrefsRepository
import com.eutechpro.smshelp.extensions.sharedPreferences
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable

/**
 * Used to schedule and un schedule sending of SMS at certain date.
 */
class SmsPersistableScheduler(val context: Context) : SmsScheduler, AnkoLogger {
    private val FREQUENCY_INTERVAL = 1000 * 10L
    private val repository = AlarmPrefsRepository(context.sharedPreferences("alarms"))

    override fun scheduleNextSms(sms: Sms): Observable<Boolean> {
        debug("Schedule Next sms")

        referenceToAlarmManager().setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                sms.date.time,
                FREQUENCY_INTERVAL,
                pendingIntentToFireOnAlarmEvent(sms.number))
        return repository.storeNextAlarmSms(sms)
    }

    override fun unscheduleNextSms(sms: Sms): Observable<Boolean> {
        debug("Un Schedule Next sms")
        referenceToAlarmManager().cancel(pendingIntentToFireOnAlarmEvent(sms.number))
        return repository.removeSmsAlarmFromStorage(sms.number)
    }

    override fun getNextScheduledSms(alarmId: Int): Observable<Sms> {
        return repository.fetchNextSms(alarmId)
    }


    private fun referenceToAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private fun pendingIntentToFireOnAlarmEvent(alarmId: Int) = PendingIntent.getBroadcast(context, alarmId, Intent(context, AlarmServiceReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
}

