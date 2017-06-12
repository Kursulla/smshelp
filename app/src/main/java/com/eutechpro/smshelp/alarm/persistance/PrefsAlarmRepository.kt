package com.eutechpro.smshelp.alarm.persistance

import android.content.SharedPreferences
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable
import java.util.*

@Suppress("ReplaceCallWithComparison", "DEPRECATION")
/**
 * Persist data in SharedPreferences
 */
class PrefsAlarmRepository(private val preferences: SharedPreferences) : AlarmRepository, AnkoLogger {
    override fun isAlarmScheduled(alarmName: String): Observable<Boolean> {
        return Observable.create {
            val exists = preferences.getLong(alarmName,0).compareTo(0) != 0
            debug("isAlarmScheduled:$exists")
            it.onNext(exists)
            it.onCompleted()
        }
    }

    override fun storeNextAlarmDate(alarmName: String, dateOfAlarmTriggering: Date): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit().putLong(alarmName, dateOfAlarmTriggering.time).commit()
            debug("stored:$stored")
            it.onNext(stored)
            it.onCompleted()
        }
    }

    override fun fetchAlarmNextTriggeringDate(alarmName: String): Observable<Date> {
        return Observable.create {
            val dateInMillis = preferences.getLong(alarmName, 0)
            if (dateInMillis.equals(0)) {
                it.onError(IllegalStateException("It looks like there is no stored Date for specified alarmName:$alarmName"))
            }
            val readedDate = Date(dateInMillis)
            debug("fetchAlarmNextTriggeringDate:$readedDate")
            it.onNext(readedDate)
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