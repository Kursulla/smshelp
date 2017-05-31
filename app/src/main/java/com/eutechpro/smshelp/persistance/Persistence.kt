package com.eutechpro.smshelp.persistance

import rx.Observable
import java.util.*

interface Persistence {
    /**
     * Store Date of next alarm.
     */
    fun storeDate(key: String, date: Date): Observable<Boolean>

    /**
     * Check is there already alarm scheduled with the same key.
     */
    fun keyExists(key: String): Observable<Boolean>

    /**
     * Read Date of next scheduled alarm
     */
    fun readDate(key: String): Observable<Date>

    /**
     * Remove Date of next alarm.
     */
    fun removeDate(key: String): Observable<Boolean>
}