package com.eutechpro.smshelp

import android.util.Log
import com.eutechpro.smshelp.extensions.SharedPreferencesForScheduler
import com.eutechpro.smshelp.persistance.Persistence
import com.eutechpro.smshelp.persistance.PreferencesPersistence
import com.eutechpro.smshelp.scheduler.AlarmScheduler
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*


internal class Model(val persistence: Persistence, val smsScheduler: AlarmScheduler) : Mvp.Model {
    private val TAG = "Model"
    private val SMS_NUMBER = "1234"
    private val isScheduledStream:PublishSubject<Boolean>  = PublishSubject.create()


    override fun nextScheduledDateStream(): Observable<Date> {
        return persistence.readDate(SMS_NUMBER)
    }

    override fun isScheduledStream(): Observable<Boolean> {
        return isScheduledStream
    }

    override fun checkStatus(){
        persistence.keyExists(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }
    }

    override fun schedule() {
        val date = Date()
        smsScheduler.scheduleNextAlarm(date, SMS_NUMBER.toInt())
        persistence.storeDate(SMS_NUMBER, date).subscribe { Log.d(TAG, "Schedule = $it") }
        persistence.keyExists(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }

    }

    override fun unSchedule() {
        smsScheduler.unScheduleNextAlarm(SMS_NUMBER.toInt())
        persistence.removeDate(SMS_NUMBER).subscribe { Log.d(TAG,"Unscheduled: $it") }
        persistence.keyExists(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }
    }
}