@file:Suppress("unused")

package com.eutechpro.smshelp.extensions

import android.app.Activity
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View

/*
 * Extensions attached to the Activity
 */

fun Activity.snackbar(@StringRes messageId: Int, anchor: View, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(anchor, messageId, length).show()
}

inline fun Activity.snackbar(@StringRes messageId: Int, anchor: View, crossinline body: () -> Unit, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(anchor, messageId, length)
            .setAction("Undo", {
                body()
            }).show()
}
