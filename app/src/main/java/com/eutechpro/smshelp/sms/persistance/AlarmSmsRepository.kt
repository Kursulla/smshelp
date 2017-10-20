package com.eutechpro.smshelp.sms.persistance

import com.eutechpro.smshelp.sms.Sms
import rx.Observable
import java.util.*

interface AlarmSmsRepository {
    /**
     * Store Date of next alarm.
     */
    fun storeNextAlarmSms(sms: Sms): Observable<Boolean>

    /**
     *  Read Sms of next scheduled alarm
     */
    fun fetchNextAlarmSms(smsNumber: Int): Observable<Sms>

    /**
     * Remove Date of next alarm.
     */
    fun removeAlarmSmsFromStorage(smsNumber: Int): Observable<Boolean>

    fun modifyDateOfAlarmSms(smsNumber: Int, date: Date): Observable<Boolean>
}