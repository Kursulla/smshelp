package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.alarm.AlarmScheduler
import com.eutechpro.smshelp.alarm.persistance.AlarmRepository
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*


internal open class Model(val alarmRepository: AlarmRepository, val smsScheduler: AlarmScheduler) : Mvp.Model, AnkoLogger {

    private val SMS_NUMBER = "1234"//todo jesus! NUMBER that is String!!!
    private val smsStream: PublishSubject<Sms> = PublishSubject.create()
    override fun checkStatus(){
        alarmRepository.fetchNextSms(SMS_NUMBER.toInt()).subscribe(
                { sms ->
                    smsStream.onNext(sms)
                },
                { throwable ->
                    smsStream.onError(throwable)
                }
        )
    }

    override fun getNextScheduledSmsStream(): Observable<Sms> {
        return smsStream
    }

    override fun schedule() {
        val sms = Sms(SMS_NUMBER, "", Date())
        alarmRepository.storeNextAlarmSms(sms).subscribe { scheduled ->
            debug("Schedule = $scheduled")
            if (scheduled) {
                smsScheduler.scheduleNextAlarm(sms)
                smsStream.onNext(sms)
            } else {
                smsStream.onError(SchedulingException("Scheduling failed!!!"))
            }
        }
    }

    override fun unSchedule() {
        alarmRepository.removeAlarmFromStorage(SMS_NUMBER).subscribe { removed ->
            debug("Unscheduled: $removed")
            if (removed) {
                smsScheduler.unScheduleNextAlarm(SMS_NUMBER.toInt())
                smsStream.onNext(null)
            } else {
                smsStream.onError(IllegalStateException("Unscheduling failed!!!"))
            }
        }
    }

    class SchedulingException(message: String?) : Throwable(message)
}