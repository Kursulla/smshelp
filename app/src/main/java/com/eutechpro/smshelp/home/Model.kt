package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.alarm.persistance.AlarmRepository
import com.eutechpro.smshelp.alarm.AlarmScheduler
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*


internal open class Model(val alarmRepository: AlarmRepository, val smsScheduler: AlarmScheduler) : Mvp.Model, AnkoLogger {
    private val SMS_NUMBER = "1234"
    private val isScheduledStream: PublishSubject<Boolean> = PublishSubject.create()


    override fun nextScheduledDateStream(): Observable<Date> {
        return alarmRepository.fetchAlarmNextTriggeringDate(SMS_NUMBER)
    }

    override fun isScheduledStream(): Observable<Boolean> {
        return isScheduledStream
    }

    override fun checkStatus(){
        alarmRepository.isAlarmScheduled(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }
    }

    override fun schedule() {
        val date = Date()
        smsScheduler.scheduleNextAlarm(date, SMS_NUMBER.toInt())
        alarmRepository.storeNextAlarmDate(SMS_NUMBER, date).subscribe { debug("Schedule = $it") }
        alarmRepository.isAlarmScheduled(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }

    }

    override fun unSchedule() {
        smsScheduler.unScheduleNextAlarm(SMS_NUMBER.toInt())
        alarmRepository.removeAlarmFromStorage(SMS_NUMBER).subscribe { debug("Unscheduled: $it") }
        alarmRepository.isAlarmScheduled(SMS_NUMBER).subscribe {
            isScheduledStream.onNext(it)
        }
    }
}