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

    override fun storeNextAlarmSms(sms: Sms): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit()
                    .putInt(SMS_NUMBER + sms.number, sms.number)
                    .putString(SMS_MESSAGE + sms.number, sms.message)
                    .putLong(SMS_DATE + sms.number, sms.date.time)
                    .commit()

            debug("stored:$stored")
            it.onNext(stored)
            it.onCompleted()
        }
    }

    override fun fetchNextSms(smsNumber: Int): Observable<Sms> {
        return Observable.create {
            val number = preferences.getInt(SMS_NUMBER + smsNumber, 0)
            val message = preferences.getString(SMS_MESSAGE + smsNumber, null)
            val dateInMillis = preferences.getLong(SMS_DATE + smsNumber, 0)
            if (number == 0 || message == null || dateInMillis < 0) {
                it.onNext(null)
            } else {
                it.onNext(Sms(number, Date(dateInMillis), message))
            }
            it.onCompleted()
        }
    }

    override fun removeSmsAlarmFromStorage(smsNumber: Int): Observable<Boolean> {
        return Observable.create<Boolean> {
            val removed = preferences.edit()
                    .remove(SMS_NUMBER + smsNumber)
                    .remove(SMS_MESSAGE + smsNumber)
                    .remove(SMS_DATE + smsNumber)
                    .commit()
            debug("removeSmsAlarmFromStorage:$removed")
            it.onNext(removed)
            it.onCompleted()
        }
    }
}