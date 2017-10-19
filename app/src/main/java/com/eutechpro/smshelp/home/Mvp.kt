package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import com.eutechpro.smshelp.sms.Sms
import java.util.*


interface Mvp {
    interface Model{
//        fun getIsAlarmScheduledStream(): rx.Observable<Boolean>
//        fun getNextScheduledDateStream(): rx.Observable<Date>
        fun getNextScheduledSmsStream(): rx.Observable<Sms>
        fun checkStatus()
        fun schedule(dateForAlarm: Date)
        fun unSchedule()
    }
    interface View{
        fun showSnackBar(@StringRes messageId: Int, anchor: android.view.View)
        fun setStatusScheduled(date: java.util.Date)
        fun setStatusNotScheduled()
        fun showError(@StringRes errorString: Int)
        fun showDatePicker()

    }
    interface Presenter{
        fun bindView(v: Mvp.View)
        fun unBindView()
        fun scheduled(viewToAttachSnackBarTo: android.view.View)

        fun checkScheduleStatus()
        fun unSchedule()
        fun schedule()
        fun scheduleAlarmForDate(dateForAlarm: Date)
    }
}