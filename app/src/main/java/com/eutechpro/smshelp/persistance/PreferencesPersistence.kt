package com.eutechpro.smshelp.persistance

import android.content.SharedPreferences
import android.util.Log
import rx.Observable
import java.util.*

@Suppress("ReplaceCallWithComparison")
/**
 * Persist data in SharedPreferences
 */
class PreferencesPersistence(private val preferences: SharedPreferences) : Persistence {
    private val TAG = "PreferencesPersistence"
    override fun keyExists(key: String): Observable<Boolean> {
        return Observable.create {
            val exists = preferences.getLong(key,0).compareTo(0) != 0
            Log.d(TAG, "keyExists:$exists")
            it.onNext(exists)
            it.onCompleted()
        }
    }

    override fun storeDate(key: String, date: Date): Observable<Boolean> {
        return Observable.create<Boolean> {
            val stored = preferences.edit().putLong(key, date.time).commit()
            Log.d(TAG, "stored:$stored")
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
            Log.d(TAG, "readDate:$readedDate")
            it.onNext(readedDate)
            it.onCompleted()
        }
    }

    override fun removeDate(key: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            val removed = preferences.edit().remove(key).commit()
            Log.d(TAG, "removeDate:$removed")
            it.onNext(removed)
            it.onCompleted()
        }
    }
}