@file:Suppress("unused")

package com.eutechpro.smshelp.extensions

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.annotation.StringRes
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


/*
 * Common extensions.
 */

@Suppress("DEPRECATION")
fun TextView.fromHtml(@StringRes textResourceId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text = Html.fromHtml(resources.getString(textResourceId), FROM_HTML_MODE_COMPACT)
    } else {
        text = Html.fromHtml(resources.getString(textResourceId))
    }
}

fun Context.sharedPreferences(): SharedPreferences {
    return getSharedPreferences("sms_help", Context.MODE_PRIVATE)
}

fun Date.format():String{
    val dateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale("sr_Latn_RS","sr_Latn_RS"))
    return dateFormat.format(this)
}

fun ImageView.load(url: String) {
    Picasso.with(context).load(url).into(this)
}