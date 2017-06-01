@file:Suppress("unused")

package com.eutechpro.smshelp.extensions

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.annotation.StringRes
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


/*
 * Common extensions.
 */

fun TextView.fromHtml(@StringRes textResourceId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text = Html.fromHtml(resources.getString(textResourceId), FROM_HTML_MODE_COMPACT)
    } else {
        text = Html.fromHtml(resources.getString(textResourceId))
    }
}

fun Context.SharedPreferencesForScheduler(): SharedPreferences {
    return getSharedPreferences("sms_help_persistance", 0)
}

fun Date.Formated(date:Date):String{
    val dateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale("sr","SR"))
    return dateFormat.format(date)
}

