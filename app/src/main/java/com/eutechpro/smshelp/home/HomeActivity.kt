package home

import android.support.annotation.StringRes
import android.widget.Button
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.Formated
import com.eutechpro.smshelp.extensions.SharedPreferencesForScheduler
import com.eutechpro.smshelp.extensions.snackbar
import com.eutechpro.smshelp.home.Model
import com.eutechpro.smshelp.home.Mvp
import com.eutechpro.smshelp.home.Presenter
import com.eutechpro.smshelp.persistance.PreferencesPersistence
import com.eutechpro.smshelp.scheduler.AlarmScheduler
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class HomeActivity : BaseActivity(), Mvp.View {
    private var statusMessage: android.widget.TextView? = null
    private var scheduleBtn: Button? = null
    private var presenter: Mvp.Presenter? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_activity)
        setSupportActionBar(find(R.id.toolbar))
        initDrawer()
        inflateContentLayout(R.layout.home_incl_content)

        statusMessage = find(R.id.status_message)
        scheduleBtn = find(R.id.tmp_schedule_button)

        //todo inject
        presenter = Presenter(Model(PreferencesPersistence(SharedPreferencesForScheduler()), AlarmScheduler(applicationContext)))
        presenter?.bindView(this)
        presenter?.checkScheduleStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unBindView()
    }

    override fun setStatusScheduled(date: java.util.Date) {
        statusMessage?.text = getString(R.string.status_scheduled, date.Formated(date))
        scheduleBtn?.text = "Un Schedule"
        scheduleBtn?.setOnClickListener {
            snackbar(R.string.snack_unscheduled, it, {
                toast("Undoooo")
                presenter?.schedule()
            })
            presenter?.unSchedule()
        }

    }

    override fun setStatusNotScheduled() {
        statusMessage?.setText(R.string.status_not_scheduled)
        scheduleBtn?.text = "Schedule"
        scheduleBtn?.setOnClickListener {
            snackbar(R.string.snack_scheduled, it, {
                toast("Undoooo")
                presenter?.unSchedule()
            })
            presenter?.schedule()
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
