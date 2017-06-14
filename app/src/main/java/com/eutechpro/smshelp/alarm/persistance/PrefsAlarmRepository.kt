package com.eutechpro.smshelp.alarm.persistance

import android.content.SharedPreferences
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable
import java.util.*

@Suppress("ReplaceCallWithComparison", "DEPRECATION")
/**
 * Persist data in SharedPreferences
 */
class PrefsAlarmRepository(private val preferences: SharedPreferences) : AlarmRepository, AnkoLogger {
    private val SMS_NUMBER = "SMS_NUMBER"
    private val SMS_MESSAGE = "SMS_MESSAGE"
    private val SMS_DATE = "SMS_DATE"

    override fun isAlarmScheduled(alarmName: String): Observable<Boolean> {
        return Observable.create {
            val exists = preferences.getLong(alarmName,0).compareTo(0) != 0
            debug("isAlarmScheduled:$exists")
            it.onNext(exists)
            it.onCompleted()
        }
    }


    override fun storeNextAlarmSms(sms: Sms): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit()
                    .putString(SMS_NUMBER, sms.number)
                    .putString(SMS_MESSAGE, sms.message)
                    .putLong(SMS_DATE, sms.date.time)
                    .commit()

            debug("stored:$stored")
            it.onNext(stored)
            it.onCompleted()
        }
    }

    override fun fetchNextSms(smsNumber: Int): Observable<Sms> {
        return Observable.create {
            val number = preferences.getString(SMS_NUMBER, null)
            val message = preferences.getString(SMS_MESSAGE, null)
            val dateInMillis = preferences.getLong(SMS_DATE, 0)
            if (number == null || message == null || dateInMillis < 0) {
                it.onNext(null)
            } else {
                it.onNext(Sms(number, message, Date(dateInMillis)))
            }
            it.onCompleted()
        }
    }

    override fun removeAlarmFromStorage(alarmName: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            val removed = preferences.edit().remove(alarmName).commit()
            debug("removeAlarmFromStorage:$removed")
            it.onNext(removed)
            it.onCompleted()
        }
    }
}