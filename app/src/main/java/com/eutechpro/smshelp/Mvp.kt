package com.eutechpro.smshelp

import android.support.annotation.StringRes


interface Mvp {
    interface Model{

    }
    interface View{
        fun showSnackBar(@StringRes messageId: Int, anchor: android.view.View)
    }
    interface Presenter{
        fun bindView(view:Mvp.View)
        fun unBindView()
        fun addNewSmsAction(viewToAttachSnackBarTo: android.view.View)

    }
}