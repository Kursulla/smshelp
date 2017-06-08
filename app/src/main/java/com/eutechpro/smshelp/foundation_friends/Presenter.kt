package com.eutechpro.smshelp.foundation_friends

import com.eutechpro.smshelp.R
import org.jetbrains.anko.AnkoLogger
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription


class Presenter(val model: Mvp.Model) : Mvp.Presenter, AnkoLogger {
    private val allSubscriptions = CompositeSubscription()
    private var view: Mvp.View? = null

    override fun bindView(view: Mvp.View) {
        this.view = view
        val subscription = model.fetchListOfFoundationFriends()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ friends ->
                    if (friends == null || friends.isEmpty()) {
                        throw IllegalStateException("Unable to load freinds of foundation. Model returned wrong/bad value")
                    }
                    view.drawFriends(friends)
                }, { throwable ->
                    throwable.printStackTrace()
                    view.showError(R.string.foundation_friends_error, true)
                })
        add(subscription)
    }

    override fun unBindView() {
        view = null
        clearSubscriptions()
    }

    fun add(subscription: Subscription) {
        allSubscriptions.add(subscription)
    }

    fun clearSubscriptions() {
        if (allSubscriptions.hasSubscriptions()) {
            allSubscriptions.clear()
        }
    }
}