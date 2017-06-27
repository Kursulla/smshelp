package com.eutechpro.smshelp.alarm.persistance

import com.eutechpro.smshelp.sms.Sms
import rx.Observable

interface AlarmRepository {
    /**
     * Store Date of next alarm.
     */
    fun storeNextAlarmSms(sms: Sms): Observable<Boolean>

    /**
     *  Read Sms of next scheduled alarm
     */
    fun fetchNextSms(smsNumber: Int): Observable<Sms>

    /**
     * Remove Date of next alarm.
     */
    fun removeSmsAlarmFromStorage(smsNumber: Int): Observable<Boolean>
}