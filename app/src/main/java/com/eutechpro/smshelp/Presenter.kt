package com.eutechpro.smshelp

import android.view.View
import org.jetbrains.anko.AnkoLogger
import rx.subscriptions.CompositeSubscription

internal class Presenter(val model: Mvp.Model) : Mvp.Presenter, AnkoLogger {
    var view: Mvp.View? = null
    val subscriptions = CompositeSubscription()

    override fun bindView(v: Mvp.View) {
        view = v
        subscriptions.add(model.isScheduledStream()
                .subscribe(
                        { isScheduled ->
                            if (isScheduled) {
                                model.nextScheduledDateStream().subscribe {
                                    view?.setStatusScheduled(it)
                                }
                            } else {
                                view?.setStatusNotScheduled()
                            }
                        },
                        { throwable ->
                            view?.showError(R.string.error_cant_check_subscription)
                            throwable.printStackTrace()
                        }))
    }

    override fun unBindView() {
        view = null
        subscriptions.clear()
    }

    override fun checkScheduleStatus() {
        model.checkStatus()
    }

    override fun scheduled(viewToAttachSnackBarTo: View) {
        view?.showSnackBar(R.string.snack_scheduled, viewToAttachSnackBarTo)//unused for now
    }

    override fun schedule() {
        model.schedule()
    }

    override fun unSchedule() {
        model.unSchedule()
    }
}