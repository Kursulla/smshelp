package com.eutechpro.executiontimelogging

import android.util.Log
import java.util.*

@Suppress("unused")
class TimeLogger(private val tag: String) {
    private val timeSplits = LinkedHashMap<String, Long>()
    private val initTime = System.currentTimeMillis()

    fun sliceTime(sliceTag: String, printNow: Boolean = true) {
        val currentTime = System.currentTimeMillis()
        timeSplits.put(sliceTag, currentTime)
        if (printNow) {
            printSlice(sliceTag, currentTime)
        }
    }

    fun printAllSlices() {
        for ((sliceTag, time) in timeSplits) {
            printSlice(sliceTag, time)
        }
    }

    fun timeFromFirstSlice(sliceTag: String, showThread: Boolean = false) {
        val currentTime = System.currentTimeMillis()
        val timeToPrint = currentTime - initTime
        if (showThread) {
            Log.d(tag, "$sliceTag: $timeToPrint in thread ${Thread.currentThread().name}")
        } else {
            Log.d(tag, "$sliceTag: $timeToPrint")
        }
    }


    private fun printSlice(sliceTag: String, time: Long) {
        val timeInMilis = time - initTime
        Log.d(tag, "$sliceTag: $timeInMilis in thread ${Thread.currentThread().name}")
    }
}