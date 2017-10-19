package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.alarm.SmsScheduler
import com.eutechpro.smshelp.sms.Sms
import org.jetbrains.anko.AnkoLogger
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*


open class Model(val smsScheduler: SmsScheduler) : Mvp.Model, AnkoLogger {
    private val SMS_NUMBER = 1234
    private val smsStream: PublishSubject<Sms> = PublishSubject.create()

    override fun checkStatus(){
        smsScheduler.getNextScheduledSms(SMS_NUMBER).subscribe(
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

    override fun schedule(dateForAlarm: Date) {
        val sms = Sms(SMS_NUMBER, dateForAlarm, "")//proveri da li stvarno trigeruje slanje na ovaj datum. trebalo bi da je sve OK, jer se samo datum promeni, al opet...
        smsScheduler.scheduleNextSms(sms)
                .subscribe { scheduled ->
                    if (scheduled) {
                        smsStream.onNext(sms)
                    } else {
                        smsStream.onError(SchedulingException("Scheduling failed!!!"))
                    }
                }
    }

    override fun unSchedule() {
        smsScheduler.unscheduleNextSms(Sms(SMS_NUMBER, Date()))
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