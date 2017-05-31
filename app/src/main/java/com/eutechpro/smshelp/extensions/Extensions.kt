@file:Suppress("unused")

package com.eutechpro.smshelp.extensions

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import java.text.SimpleDateFormat
import java.util.*


fun Activity.snackbar(@StringRes messageId: Int, anchor: View, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(anchor, messageId, length).show()
}

inline fun Activity.snackbar(@StringRes messageId: Int, anchor: View, crossinline body: () -> Unit, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(anchor, messageId, length)
            .setAction("Undo", {
                body()
            }).show()
}

fun Context.SharedPreferencesForScheduler(): SharedPreferences {
    return getSharedPreferences("sms_help_persistance", 0)
}
fun Date.Formated(date:Date):String{
    val dateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale("sr","SR"))
    return dateFormat.format(date)
}

