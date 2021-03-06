package com.eutechpro.smshelp

import android.support.annotation.StringRes
import rx.Observable
import java.util.*


internal interface Mvp {
    interface Model{
        fun isScheduledStream(): Observable<Boolean>
        fun nextScheduledDateStream(): Observable<Date>
        fun checkStatus()
        fun schedule()
        fun unSchedule()
    }
    interface View{
        fun showSnackBar(@StringRes messageId: Int, anchor: android.view.View)
        fun setStatusScheduled(date: Date)
        fun setStatusNotScheduled()
    }
    interface Presenter{
        fun bindView(view:Mvp.View)
        fun unBindView()
        fun scheduled(viewToAttachSnackBarTo: android.view.View)

        fun checkScheduleStatus()
        fun unSchedule()
        fun schedule()
    }
}