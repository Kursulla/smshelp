package com.eutechpro.smshelp.sms

import android.telephony.SmsManager
import java.util.*

class SmsControll {
    fun sendSms(smsNumber: String, smsMessage: String) {
        SmsManager.getDefault().sendTextMessage(smsNumber, null, smsMessage, null, null)
        val sms = Sms(smsNumber,smsMessage, Date())

        // Ovo sacuvati u nekom repository...
        // Store this information
        // Create entity we want to store for later: time and date
    }
}