@file:Suppress("unused")

package com.eutechpro.smshelp.extensions

import android.app.Activity
import android.content.res.AssetManager
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

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

fun AssetManager.loadJsonDataFromAssets(fileName: String): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = open("data/$fileName")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = buffer.toString(Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return json
}