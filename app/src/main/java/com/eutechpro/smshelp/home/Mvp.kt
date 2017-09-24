package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import com.eutechpro.smshelp.sms.Sms


interface Mvp {
    interface Model{
//        fun getIsAlarmScheduledStream(): rx.Observable<Boolean>
//        fun getNextScheduledDateStream(): rx.Observable<Date>
        fun getNextScheduledSmsStream(): rx.Observable<Sms>
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