package com.eutechpro.smshelp.sms

import java.util.*

data class Sms(val number: Int, var date: Date, var message: String? = null)