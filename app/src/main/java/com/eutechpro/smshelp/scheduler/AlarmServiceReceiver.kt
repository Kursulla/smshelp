package com.eutechpro.smshelp.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.eutechpro.smshelp.extensions.sharedPreferences
import com.eutechpro.smshelp.notifications.SmsNotificationsManager
import com.eutechpro.smshelp.sms.Sms
import com.eutechpro.smshelp.sms.persistance.SmsPrefsRepository
import com.eutechpro.smshelp.sms.persistance.SmsRepository
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.util.*

/*
 *   Why we are passing only sms_number and not whole SMS?
 *   Here is the reason: https://stackoverflow.com/a/41429570/1632988
 *
 */
class AlarmServiceReceiver : BroadcastReceiver(), AnkoLogger {
    companion object {
        const val SMS_NUMBER_KEY = "com.eutechpro.smshelp.SMS_NUMBER_KEY"
    }

    private lateinit var alarmSmsRepository: SmsRepository

    override fun onReceive(context: Context, intent: Intent) {
        info("onReceive")
        val smsNumber = intent.getIntExtra(SMS_NUMBER_KEY, -1)
        info { "Send sms to $smsNumber" }
        if(smsNumber == -1){
            info("Unable to retreive appropriate SMS_NUMBER_KEY")
            return
        }
        //todo inject
        SmsNotificationsManager().showNotification(context)

        //todo inject
        alarmSmsRepository = SmsPrefsRepository(context.sharedPreferences())
        alarmSmsRepository
                .fetchNextSms(smsNumber)
                .subscribeOn(Schedulers.io())
                .subscribe(ChangeSmsAlarmAction(alarmSmsRepository), FetchingAlarmSmsErrorAction())
    }

    private class ChangeSmsAlarmAction(val alarmRepository: SmsRepository) : Action1<Sms>, AnkoLogger {
        override fun call(sms: Sms?) {
            if (sms == null) {
                return
            }
            val newDate = changeSmsDateByOneMonth(sms.date)
            alarmRepository.modifyDateOfSms(sms.number, newDate)
                    .subscribe(
                            { modified -> info("date modified:$modified") },
                            { throwable -> info("error saving: $throwable") }
                    )
        }

        private fun changeSmsDateByOneMonth(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date.time
            calendar.add(Calendar.MONTH, 1)

            return calendar.time
        }
    }

    private class FetchingAlarmSmsErrorAction : Action1<Throwable>, AnkoLogger {
        override fun call(throwable: Throwable?) {
            info("Some shit happened: $throwable")
        }

    }


}