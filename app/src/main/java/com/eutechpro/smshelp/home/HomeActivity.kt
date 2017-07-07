package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.ToolbarlessActivity
import com.eutechpro.smshelp.extensions.format
import com.eutechpro.smshelp.extensions.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject


class HomeActivity : ToolbarlessActivity(), Mvp.View {
    @Inject
    internal lateinit var presenter: Mvp.Presenter
    private val statusMessage: TextView get() = find(R.id.status_message)
    private val statusDescription: TextView get() = find(R.id.status_description)
    private val scheduleBtn: Button get() = find(R.id.tmp_schedule_button)
    private val heart: ImageView get() = find(R.id.hearth)

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
        heart.setImageResource(R.drawable.heart_full)
        statusMessage.text = getString(R.string.scheduled_status_title, date.format())
        statusDescription.text = getString(R.string.scheduled_status_description)
        scheduleBtn.text = getString(R.string.scheduled_btn)
        scheduleBtn.setOnClickListener {
            presenter.unSchedule()
        }

    }

    override fun setStatusNotScheduled() {
        heart.setImageResource(R.drawable.heart_broken)
        statusMessage.setText(R.string.not_scheduled_status_title)
        statusDescription.text = getString(R.string.not_scheduled_status_description)
        scheduleBtn.text = getString(R.string.not_scheduled_btn)
        scheduleBtn.setOnClickListener {
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
