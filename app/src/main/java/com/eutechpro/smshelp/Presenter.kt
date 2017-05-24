package com.eutechpro.smshelp

import android.view.View

class Presenter(val model: Mvp.Model) : Mvp.Presenter {
    var view: Mvp.View? = null

    override fun unBindView() {
        view = null
    }


    override fun bindView(view: Mvp.View) {
        this.view = view
    }

    override fun addNewSmsAction(viewToAttachSnackBarTo: View) {

        view?.showSnackBar(R.string.nav_become_donator, viewToAttachSnackBarTo)
    }

}