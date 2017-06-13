package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import java.util.*


internal interface Mvp {
    interface Model{
        fun isScheduledStream(): rx.Observable<Boolean>
        fun nextScheduledDateStream(): rx.Observable<Date>
        fun checkStatus()
        fun schedule()
        fun unSchedule()
    }
    interface View{
        fun showSnackBar(@StringRes messageId: Int, anchor: android.view.View)
        fun setStatusScheduled(date: java.util.Date)
        fun setStatusNotScheduled()
        fun showError(@StringRes errorString: Int)
    }
    interface Presenter{
        fun bindView(v: Mvp.View)
        fun unBindView()
        fun scheduled(viewToAttachSnackBarTo: android.view.View)

        fun checkScheduleStatus()
        fun unSchedule()
        fun schedule()
    }
}