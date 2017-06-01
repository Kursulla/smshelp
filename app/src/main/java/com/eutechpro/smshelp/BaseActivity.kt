package com.eutechpro.smshelp

import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.eutechpro.smshelp.become_volunteer.AboutUsActivity
import com.eutechpro.smshelp.become_volunteer.BecomeDonatorActivity
import com.eutechpro.smshelp.become_volunteer.BecomeVolunteerActivity
import com.eutechpro.smshelp.become_volunteer.NeedHelpActivity
import home.HomeActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        val MENU_HOME_POSITION: Int = 0
        val MENU_BECOME_VOLUNTEER_POSITION: Int = 1
        val MENU_BECOME_DONATOR_POSITION: Int = 2
        val MENU_NEED_HELP_POSITION: Int = 3
        val MENU_ABOUT_US_POSITION: Int = 4
    }

    protected val navigationView: NavigationView
        get() {
            return find(R.id.nav_view)
        }

    protected fun initLayout(@IdRes contentLayoutId: Int) {
        setContentView(R.layout.drawer_activity)
        setSupportActionBar(find(R.id.toolbar))
        initDrawer()
        inflateContentLayout(contentLayoutId)
        selectMenuItem()
    }

    protected abstract fun selectMenuItem()

    private fun initDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, find(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_donate_sms -> startActivity<HomeActivity>()
                R.id.nav_become_volunteer -> startActivity<BecomeVolunteerActivity>()
                R.id.nav_become_donator -> startActivity<BecomeDonatorActivity>()
                R.id.nav_need_help -> startActivity<NeedHelpActivity>()
                R.id.nav_about_us -> startActivity<AboutUsActivity>()
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun inflateContentLayout(@IdRes contentId: Int) {
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
}
