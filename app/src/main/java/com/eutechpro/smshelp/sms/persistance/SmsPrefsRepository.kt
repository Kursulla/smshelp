package com.eutechpro.smshelp.sms.persistance

import android.content.SharedPreferences
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import java.util.*

@Suppress("ReplaceCallWithComparison", "DEPRECATION")
/**
 * Persist data in SharedPreferences
 */
class SmsPrefsRepository(private val preferences: SharedPreferences) : SmsRepository, AnkoLogger {
    private val SMS_NUMBER = "SMS_NUMBER_KEY"
    private val SMS_MESSAGE = "SMS_MESSAGE"
    private val SMS_DATE = "SMS_DATE"

    override fun storeNextSms(sms: Sms): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit()
                    .putInt(SMS_NUMBER + sms.number, sms.number)
                    .putString(SMS_MESSAGE + sms.number, sms.message)
                    .putLong(SMS_DATE + sms.number, sms.date.time)
                    .commit()

            info("stored:$stored")
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

    override fun removeSms(smsNumber: Int): Observable<Boolean> {
        return Observable.create<Boolean> {
            val removed = preferences.edit()
                    .remove(SMS_NUMBER + smsNumber)
                    .remove(SMS_MESSAGE + smsNumber)
                    .remove(SMS_DATE + smsNumber)
                    .commit()
            info("removeSms:$removed")
            it.onNext(removed)
            it.onCompleted()
        }
    }

    override fun modifyDateOfSms(smsNumber: Int, date: Date): Observable<Boolean> {
        return Observable.create<Boolean> {
            val modified = preferences.edit()
                    .putLong(SMS_DATE + smsNumber, date.time)
                    .commit()
            info("modifyDateOfSms:$modified")
            it.onNext(modified)
            it.onCompleted()
        }
    }
}