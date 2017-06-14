package com.eutechpro.smshelp.alarm.persistance

import com.eutechpro.smshelp.sms.Sms
import rx.Observable

interface AlarmRepository {
    /**
     * Store Date of next alarm.
     */
    fun storeNextAlarmSms(sms: Sms): Observable<Boolean>

    /**
     * Check is there already alarm scheduled with the same alarmName.
     */
    fun isAlarmScheduled(alarmName: String): Observable<Boolean>

    /**
     *  Read Sms of next scheduled alarm
     */
    fun fetchNextSms(smsNumber: Int): Observable<Sms>

    /**
     * Remove Date of next alarm.
     */
    fun removeAlarmFromStorage(alarmName: String): Observable<Boolean>
}