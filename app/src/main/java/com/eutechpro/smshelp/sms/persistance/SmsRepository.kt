package com.eutechpro.smshelp.sms.persistance

import com.eutechpro.smshelp.sms.Sms
import rx.Observable
import java.util.*

interface SmsRepository {
    /**
     * Store Date of next alarm.
     */
    fun storeNextSms(sms: Sms): Observable<Boolean>

    /**
     *  Read Sms of next scheduled alarm
     */
    fun fetchNextSms(smsNumber: Int): Observable<Sms>

    /**
     * Remove Date of next alarm.
     */
    fun removeSms(smsNumber: Int): Observable<Boolean>

    /**
     * Change Date of Sms.
     */
    fun modifyDateOfSms(smsNumber: Int, date: Date): Observable<Boolean>
}