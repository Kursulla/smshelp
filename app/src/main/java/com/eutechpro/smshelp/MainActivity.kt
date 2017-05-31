package com.eutechpro.smshelp

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.eutechpro.smshelp.extensions.Formated
import com.eutechpro.smshelp.extensions.SharedPreferencesForScheduler
import com.eutechpro.smshelp.extensions.snackbar
import com.eutechpro.smshelp.persistance.PreferencesPersistence
import com.eutechpro.smshelp.scheduler.AlarmScheduler
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : AppCompatActivity(),Mvp.View {
    private var statusMessage: TextView? = null
    private var fab: FloatingActionButton? = null
    private var presenter: Mvp.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(find(R.id.toolbar))
        initDrawer()

        statusMessage = find(R.id.status_message)
        fab = find(R.id.fab)

        //todo inject
        presenter = Presenter(Model(PreferencesPersistence(SharedPreferencesForScheduler()), AlarmScheduler(applicationContext)))
        presenter?.bindView(this)
        presenter?.checkScheduleStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unBindView()
    }

    override fun setStatusScheduled(date: Date) {
        statusMessage?.text = getString(R.string.status_scheduled, date.Formated(date))
        fab?.setImageResource(R.drawable.ic_menu_manage)
        fab?.setOnClickListener {
            snackbar(R.string.snack_unscheduled, it, {
                toast("Undoooo")
                presenter?.schedule()
            })
            presenter?.unSchedule()
        }

    }

    override fun setStatusNotScheduled() {
        statusMessage?.setText(R.string.status_not_scheduled)
        fab?.setImageResource(R.drawable.ic_menu_send)
        fab?.setOnClickListener {
            snackbar(R.string.snack_scheduled, it, {
                toast("Undoooo")
                presenter?.unSchedule()
            })
            presenter?.schedule()
        }

    }

    override fun showSnackBar(@StringRes messageId: Int, anchor: View) {
        snackbar(messageId, anchor, {
            toast("Undoooo")
        })
    }

    override fun showError(errorString: Int) {
        throw UnsupportedOperationException("This method is still not implemented")
    }

    private fun initDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, find(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView:NavigationView = find(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                //todo add listeners for drawer menu
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }


    override fun onBackPressed() {
        val drawer:DrawerLayout = find(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
