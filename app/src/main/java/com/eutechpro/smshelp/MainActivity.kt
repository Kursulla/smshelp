package com.eutechpro.smshelp

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.eutechpro.smshelp.sms.SmsControll

class MainActivity : AppCompatActivity(),Mvp.View {
    var fab:FloatingActionButton? = null
    val presenter: Mvp.Presenter = Presenter(Model())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        initDrawer()
//            val smsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage("+4917637206586", null, "sms message", null, null)
        presenter.bindView(this)

        fab = findViewById(R.id.fab) as FloatingActionButton
        fab?.setOnClickListener { view ->
            presenter.addNewSmsAction(view)
        }


        val smsControll = SmsControll()
        smsControll.scheduleNextAlarm(this)

    }

    private fun initDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, findViewById(R.id.toolbar) as Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                //todo add listeners for drawer menu
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun showSnackBar(@StringRes messageId: Int, anchor: View){
        Snackbar.make(anchor, messageId, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }












    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
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
