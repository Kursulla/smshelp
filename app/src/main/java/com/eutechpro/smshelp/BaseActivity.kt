package com.eutechpro.smshelp

import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.eutechpro.smshelp.become_volunteer.BecomeDonatorActivity
import com.eutechpro.smshelp.become_volunteer.BecomeVolunteerActivity
import home.HomeActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

open class BaseActivity : AppCompatActivity() {
    protected fun initDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, find(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = find(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_donate_sms -> startActivity<HomeActivity>()
                R.id.nav_become_volunteer -> startActivity<BecomeVolunteerActivity>()
                R.id.nav_become_donator -> startActivity<BecomeDonatorActivity>()
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    protected fun inflateContentLayout(@IdRes contentId: Int) {
        layoutInflater.inflate(contentId, find(R.id.content_holder))
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = find(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {

                return true
            }


            else -> return super.onOptionsItemSelected(item)
        }
    }
}
