package com.eutechpro.smshelp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eutechpro.smshelp.extensions.sharedPreferences
import com.eutechpro.smshelp.sms.Sms
import com.eutechpro.smshelp.sms.persistance.AlarmSmsPrefsRepository
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable

@Suppress("PrivatePropertyName")
/**
 * Used to schedule and un schedule sending of SMS at certain date.
 */
//todo Scheduler have to schedule and nothing else! Move Repository out of scheduler and do it, if needed, in Model!
class PersistableSmsAlarmScheduler(val context: Context) : SmsAlarmScheduler, AnkoLogger {
    private val FREQUENCY_INTERVAL = 1000 * 5L
    private val repository = AlarmSmsPrefsRepository(context.sharedPreferences())

    override fun scheduleNextSms(sms: Sms): Observable<Boolean> {
        info { "Schedule Next sms" }
        scheduleAlarmForSms(sms)
        return repository.storeNextAlarmSms(sms)
    }

    override fun unscheduleNextSms(smsNumber: Int): Observable<Boolean> {
        info("Un Schedule Next sms")
        unscheduleAlarmForSms(smsNumber)
        return repository.removeAlarmSmsFromStorage(smsNumber)
    }

    override fun getNextScheduledSms(smsNumber: Int): Observable<Sms> {
        return repository.fetchNextAlarmSms(smsNumber)
    }

    private fun scheduleAlarmForSms(sms: Sms) {
        getAlarmManager().setInexactRepeating(AlarmManager.RTC_WAKEUP, sms.date.time,
                FREQUENCY_INTERVAL, pendingIntentToFireOnAlarmEvent(sms.number))
    }

    private fun unscheduleAlarmForSms(smsNumber: Int) {
        getAlarmManager().cancel(pendingIntentToFireOnAlarmEvent(smsNumber))
    }

    private fun getAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun pendingIntentToFireOnAlarmEvent(smsNumber: Int): PendingIntent? {
        val intent = Intent(context, AlarmServiceReceiver::class.java)
        intent.putExtra(AlarmServiceReceiver.SMS_NUMBER_KEY, smsNumber)
        return PendingIntent.getBroadcast(
                context,
                smsNumber,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}

