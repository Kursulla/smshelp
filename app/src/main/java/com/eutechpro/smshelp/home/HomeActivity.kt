package com.eutechpro.smshelp.home

import android.support.annotation.StringRes
import android.util.Log
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.BaseActivityTransparentToolbar
import com.eutechpro.smshelp.R

import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.extensions.format
import com.eutechpro.smshelp.extensions.snackbar
import com.eutechpro.smshelp.home.picker.DatePickerFragment
import kotlinx.android.synthetic.main.home_incl_content.*
import org.jetbrains.anko.toast
import java.util.*


open class HomeActivity : BaseActivityTransparentToolbar(), Mvp.View {
    internal lateinit var presenter: Mvp.Presenter

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.home_incl_content)
        SmsHelpApplication.homeDaggerComponent.inject(this)
        presenter = SmsHelpApplication.homeDaggerComponent.getPresenter()

    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
        presenter.checkScheduleStatus()
    }

    override fun onPause() {
        super.onPause()
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

    override fun showDatePicker() {
        val dateDialog = DatePickerFragment()
        dateDialog.onDaySelectedListener = object: DatePickerFragment.OnDaySelected {
            override fun dateSelected(date: Date) {
                Log.d("TAG", ": ")
                presenter.scheduleAlarmForDate(date)
            }
        }
        dateDialog.show(supportFragmentManager, "date_picker")
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
