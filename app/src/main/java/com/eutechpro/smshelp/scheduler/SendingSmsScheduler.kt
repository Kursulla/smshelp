package com.eutechpro.smshelp.scheduler

import com.eutechpro.smshelp.sms.Sms

interface SendingSmsScheduler {
    fun scheduleNextSms(sms: Sms)
    fun unscheduleNextSms(smsNumber: Int)
}