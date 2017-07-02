package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.R

class Presenter(val model: Mvp.Model) : Mvp.Presenter, org.jetbrains.anko.AnkoLogger {
    var view: Mvp.View? = null
    val subscriptions = rx.subscriptions.CompositeSubscription()

    override fun bindView(v: Mvp.View) {
        view = v

        subscriptions.add(model.getNextScheduledSmsStream().subscribe(
                { sms ->
                    if (sms != null) {
                        view?.setStatusScheduled(sms.date)
                    } else {
                        view?.setStatusNotScheduled()
                    }
                },
                { throwable ->
                    view?.showError(R.string.error_cant_check_subscription)
                    view?.setStatusNotScheduled()
                    throwable.printStackTrace()
                })
        )

    }

    override fun unBindView() {
        view = null
        subscriptions.clear()
    }

    override fun checkScheduleStatus() {
        model.checkStatus()
    }

    override fun scheduled(viewToAttachSnackBarTo: android.view.View) {
        view?.showSnackBar(R.string.snack_scheduled, viewToAttachSnackBarTo)//unused for now
    }

    override fun schedule() {
        model.schedule()
    }

    override fun unSchedule() {
        model.unSchedule()
    }
}
