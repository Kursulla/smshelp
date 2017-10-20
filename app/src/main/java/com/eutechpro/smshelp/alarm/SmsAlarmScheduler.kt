package com.eutechpro.smshelp.alarm

import com.eutechpro.smshelp.sms.Sms
import rx.Observable

interface SmsAlarmScheduler {
    fun scheduleNextSms(sms: Sms): Observable<Boolean>
    fun unscheduleNextSms(smsNumber: Int): Observable<Boolean>
    fun getNextScheduledSms(smsNumber: Int): Observable<Sms>
}