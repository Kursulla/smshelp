package com.eutechpro.smshelp.alarm.persistance

import rx.Observable
import java.util.*

interface AlarmRepository {
    /**
     * Store Date of next alarm.
     */
    fun storeNextAlarmDate(alarmName: String, dateOfAlarmTriggering: Date): Observable<Boolean>

    /**
     * Check is there already alarm scheduled with the same alarmName.
     */
    fun isAlarmScheduled(alarmName: String): Observable<Boolean>

    /**
     * Read Date of next scheduled alarm
     */
    fun fetchAlarmNextTriggeringDate(alarmName: String): Observable<Date>

    /**
     * Remove Date of next alarm.
     */
    fun removeAlarmFromStorage(alarmName: String): Observable<Boolean>
}