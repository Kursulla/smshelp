package com.eutechpro.smshelp.sms

import android.content.Context
import com.eutechpro.smshelp.extensions.sharedPreferences
import rx.Observable
import java.util.*

class SmsPrefsRepository(val context: Context) : SmsRepository {
    private val PREFS_NAME = "sms_repository"
    private val SMS_NUMBER_KEY = "sms_number"
    private val SMS_MESSAGE_KEY = "sms_message"
    private val SMS_DATE_KEY = "sms_date_timestamp"
    private val TOTAL_DONATED_MONEY_KEY = "total_donated_money"

    override fun storeLastSms(sms: Sms): Observable<Boolean> {
        return Observable.create {
            val prefs = context.sharedPreferences(PREFS_NAME)
            val editor = prefs.edit()
            editor.putInt(SMS_NUMBER_KEY, sms.number)
            editor.putString(SMS_MESSAGE_KEY, sms.message)
            editor.putLong(SMS_DATE_KEY, sms.date.time)
            val status = editor.commit()
            it.onNext(status)
            it.onCompleted()
        }

    }

    override fun fetchLastSms(): Observable<Sms> {
        return Observable.create {
            val prefs = context.sharedPreferences(PREFS_NAME)
            val num = prefs.getInt(SMS_NUMBER_KEY, 0)
            val message = prefs.getString(SMS_MESSAGE_KEY, null)
            val dateTimestamp = prefs.getLong(SMS_DATE_KEY, -1)
            it.onNext(Sms(num, Date(dateTimestamp), message))
            it.onCompleted()
        }
    }

    override fun getTotalDonatedMoney(): Observable<Int> {
        return Observable.create {
            val prefs = context.sharedPreferences(PREFS_NAME)
            val donated = prefs.getInt(TOTAL_DONATED_MONEY_KEY, -1)
            it.onNext(donated)
            it.onCompleted()
        }

    }
}