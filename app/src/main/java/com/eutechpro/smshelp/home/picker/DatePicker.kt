package com.eutechpro.smshelp.home.picker

import android.app.DatePickerDialog
import android.content.Context
import com.eutechpro.executiontimelogging.TimeLogger
import org.jetbrains.anko.AnkoLogger
import java.util.*

class DatePicker(private val context: Context) : AnkoLogger {
    var onDaySelectedListener: OnDaySelected? = null
    private val dialog:DatePickerDialog
    init{
        val timeLogger = TimeLogger("DatePicker logger")
        timeLogger.sliceTime("onCreateDialog 1")
        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)
        val currentDay = c.get(Calendar.MONTH)

        dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            run {
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.YEAR, year)
                selectedCalendar.set(Calendar.MONTH, month)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, day)
                selectedCalendar.add(Calendar.MINUTE, 1)

                onDaySelectedListener?.dateSelected(selectedCalendar.time)
            }
        }, currentYear, currentDay, 1)
        dialog.datePicker.minDate = c.timeInMillis
        timeLogger.sliceTime("onCreateDialog 2")
    }


    fun show() {
        if (onDaySelectedListener == null) {
            throw  IllegalStateException("You have to provide OnDaySelected listener! Without that, this does not make sence!")
        }
        dialog.show()
    }

    interface OnDaySelected {
        fun dateSelected(date: Date)
    }
}