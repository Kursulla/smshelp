package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import android.widget.Button
import android.widget.TextView
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.ToolbarlessActivity
import com.eutechpro.smshelp.extensions.Formated
import com.eutechpro.smshelp.extensions.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject


class HomeActivity : ToolbarlessActivity(), Mvp.View {
    @Inject
    internal lateinit var presenter: Mvp.Presenter
    private val statusMessage: TextView get() = find(R.id.status_message)
    private val scheduleBtn: Button get() = find(R.id.tmp_schedule_button)

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.home_incl_content)

        SmsHelpApplication.homeDaggerComponent.inject(this)
        presenter.bindView(this)
        presenter.checkScheduleStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBindView()
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_HOME_POSITION).isChecked = true
    }
    override fun setStatusScheduled(date: java.util.Date) {
        statusMessage.text = getString(R.string.status_scheduled, date.Formated(date))
        scheduleBtn.text = "Un Schedule"
        scheduleBtn.setOnClickListener {
            snackbar(R.string.snack_unscheduled, it, {
                toast("Undoooo")
                presenter.schedule()
            })
            presenter.unSchedule()
        }

    }

    override fun setStatusNotScheduled() {
        statusMessage.setText(R.string.status_not_scheduled)
        scheduleBtn.text = "Schedule"
        scheduleBtn.setOnClickListener {
            snackbar(R.string.snack_scheduled, it, {
                toast("Undoooo")
                presenter.unSchedule()
            })
            presenter.schedule()
        }
    }

    override fun showSnackBar(@StringRes messageId: Int, anchor: android.view.View) {
        snackbar(messageId, anchor, {
            toast("Undoooo")
        })
    }

    override fun showError(errorString: Int) {
        throw UnsupportedOperationException("This method is still not implemented")
    }
}
