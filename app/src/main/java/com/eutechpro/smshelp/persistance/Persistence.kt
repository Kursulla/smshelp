package com.eutechpro.smshelp.persistance

import rx.Observable
import java.util.*


interface Persistence {
    fun storeDate(key: String, date: Date): Observable<Boolean>
    fun keyExists(key: String): Observable<Boolean>
    fun readDate(key: String): Observable<Date>
    fun removeDate(key: String): Observable<Boolean>
}