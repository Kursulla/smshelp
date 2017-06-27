package com.eutechpro.smshelp.sms

interface SmsRepository {
    fun storeLastSms(sms: Sms): rx.Observable<Boolean>
    fun fetchLastSms(): rx.Observable<Sms>
    fun getTotalDonatedMoney(): rx.Observable<Int>
}