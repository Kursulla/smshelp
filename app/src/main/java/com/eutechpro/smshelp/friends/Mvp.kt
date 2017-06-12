package com.eutechpro.smshelp.friends

import android.support.annotation.StringRes
import rx.Observable
import java.util.*

interface Mvp {
    interface Model {
        fun fetchListOfFoundationFriends(): Observable<ArrayList<Friend>>
    }

    interface Presenter {
        fun bindView(view: View)
        fun unBindView()
    }

    interface View {
        fun showError(@StringRes messageResId: Int, shouldKillTheActivity: Boolean)
        fun drawFriends(friends: ArrayList<Friend>)
    }
}