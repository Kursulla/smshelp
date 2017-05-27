package com.eutechpro.smshelp.sms

import android.telephony.SmsManager

class SmsControll {
    fun sendSms(smsNumber: String, smsMessage: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(smsNumber, null, smsMessage, null, null)
    }
}