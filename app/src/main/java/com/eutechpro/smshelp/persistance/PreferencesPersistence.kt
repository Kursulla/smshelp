package com.eutechpro.smshelp.persistance

import android.content.SharedPreferences
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import rx.Observable
import java.util.*

@Suppress("ReplaceCallWithComparison", "DEPRECATION")
/**
 * Persist data in SharedPreferences
 */
class PreferencesPersistence(private val preferences: SharedPreferences) : Persistence, AnkoLogger {
    override fun keyExists(key: String): Observable<Boolean> {
        return Observable.create {
            val exists = preferences.getLong(key,0).compareTo(0) != 0
            debug("keyExists:$exists")
            it.onNext(exists)
            it.onCompleted()
        }
    }

    override fun storeDate(key: String, date: Date): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit().putLong(key, date.time).commit()
            debug("stored:$stored")
            it.onNext(stored)
            it.onCompleted()
        }
    }

    override fun readDate(key: String): Observable<Date> {
        return Observable.create {
            val dateInMillis = preferences.getLong(key, 0)
            if (dateInMillis.equals(0)) {
                it.onError(IllegalStateException("It looks like there is no stored Date for specified key:$key"))
            }
            val readedDate = Date(dateInMillis)
            debug("readDate:$readedDate")
            it.onNext(readedDate)
            it.onCompleted()
        }
    }

    override fun removeDate(key: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            val removed = preferences.edit().remove(key).commit()
            debug("removeDate:$removed")
            it.onNext(removed)
            it.onCompleted()
        }
    }
}