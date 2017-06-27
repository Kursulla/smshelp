package com.eutechpro.smshelp.alarm

import com.eutechpro.smshelp.sms.Sms
import rx.Observable

interface SmsScheduler {
    fun scheduleNextSms(sms: Sms): Observable<Boolean>
    fun unscheduleNextSms(sms: Sms): Observable<Boolean>
    fun getNextScheduledSms(alarmId: Int): Observable<Sms>
}