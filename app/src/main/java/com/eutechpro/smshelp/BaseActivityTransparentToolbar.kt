package com.eutechpro.smshelp

import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.eutechpro.smshelp.about.AboutUsActivity
import com.eutechpro.smshelp.donator.BecomeDonatorActivity
import com.eutechpro.smshelp.friends.FriendsActivity
import com.eutechpro.smshelp.help.NeedHelpActivity
import com.eutechpro.smshelp.home.HomeActivity
import com.eutechpro.smshelp.volunteer.BecomeVolunteerActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

abstract class BaseActivityTransparentToolbar : AppCompatActivity() {
    protected val navigationView: NavigationView get() {
        return find(R.id.nav_view)
    }

    protected val toolbar: Toolbar get() {
        return find(R.id.toolbar)
    }

    protected fun initLayout(@IdRes contentLayoutId: Int) {
        setContentView(R.layout.base_trasparent_toolbar_activity)
        toolbar.title = ""
        setSupportActionBar(toolbar)
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
                R.id.nav_friends -> startActivity<FriendsActivity>()
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
