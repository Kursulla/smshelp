package com.eutechpro.smshelp.extensions

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


fun Activity.snackbar(@StringRes messageId: Int, anchor: View) {
    Snackbar.make(anchor, messageId, Snackbar.LENGTH_LONG)
            .setAction("Undo", {
                toast("Prc todo")
            }).show()
}


fun Context.SharedPreferencesForScheduler(): SharedPreferences {
    return getSharedPreferences("sms_help_persistance", 0)
}
fun Date.Formated(date:Date):String{
    val dateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale("sr","SR"))
    return dateFormat.format(date)
}
