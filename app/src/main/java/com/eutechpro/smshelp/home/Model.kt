package com.eutechpro.smshelp.home


import com.eutechpro.smshelp.Constants
import com.eutechpro.smshelp.scheduler.SendingSmsScheduler
import com.eutechpro.smshelp.sms.Sms
import com.eutechpro.smshelp.sms.persistance.SmsRepository
import org.jetbrains.anko.AnkoLogger
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*


open class Model(private val smsAlarmScheduler: SendingSmsScheduler, private val repository: SmsRepository) : Mvp.Model, AnkoLogger {
    private val smsStream: PublishSubject<Sms> = PublishSubject.create()

    override fun checkStatus(){
        repository.fetchNextSms(Constants.SMS_NUMBER).subscribe(
                { sms ->
                    smsStream.onNext(sms)
                },
                { throwable ->
                    smsStream.onError(throwable)
                }
        )
    }

    override fun getNextScheduledSmsStream(): Observable<Sms> = smsStream

    override fun schedule(dateForAlarm: Date) {
        val sms = Sms(Constants.SMS_NUMBER, dateForAlarm, "")//Right now It does not matter what we send
        smsAlarmScheduler.scheduleNextSms(sms)
        repository.storeNextSms(sms).subscribe { scheduled ->
                    if (scheduled) {
                        smsStream.onNext(sms)
                    } else {
                        smsStream.onError(SchedulingException("Scheduling failed!!!"))
                    }
                }
    }

    override fun unSchedule() {
        smsAlarmScheduler.unscheduleNextSms(Constants.SMS_NUMBER)
        repository.removeSms(Constants.SMS_NUMBER)
                .subscribe { removed ->
                    if (removed) {
                        smsStream.onNext(null)
                    } else {
                        smsStream.onError(SchedulingException("Unscheduling failed!!!"))
                    }
                }
    }

    class SchedulingException(message: String?) : Throwable(message)
}