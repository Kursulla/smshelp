package com.eutechpro.smshelp.sms

import android.telephony.SmsManager
import java.util.*

class SmsControll {
    fun sendSms(smsNumber: Int, smsMessage: String) {
        SmsManager.getDefault().sendTextMessage(smsNumber.toString(), null, smsMessage, null, null)
        val sms = Sms(smsNumber, Date(), smsMessage)

        // Ovo sacuvati u nekom repository...
        // Store this information
        // Create entity we want to store for later: time and date
    }
}