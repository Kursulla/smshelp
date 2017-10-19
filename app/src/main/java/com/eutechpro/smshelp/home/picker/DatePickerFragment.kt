package com.eutechpro.smshelp.home.picker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
    lateinit var onDaySelectedListener: OnDaySelected

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
//        c.add(Calendar.MONTH, 1)
//        c.set(Calendar.DAY_OF_MONTH, 1)
        val currentYear = c.get(Calendar.YEAR)
        val currentDay = c.get(Calendar.MONTH)

        val dialog = DatePickerDialog(context, OnDateSetListener { _, year, month, day ->
            run {
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.YEAR, year)
                selectedCalendar.set(Calendar.MONTH, month)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, day)
                selectedCalendar.add(Calendar.MINUTE, 1)

                onDaySelectedListener.dateSelected(selectedCalendar.time)
            }
        }, currentYear, currentDay, 1)
        dialog.datePicker.minDate = c.timeInMillis
        return dialog
    }

    interface OnDaySelected {
        fun dateSelected(date: Date)
    }
}