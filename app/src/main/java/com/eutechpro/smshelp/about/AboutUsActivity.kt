package com.eutechpro.smshelp.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.custom_views.TextViewFont
import com.eutechpro.smshelp.extensions.fromHtml
import org.jetbrains.anko.find


class AboutUsActivity : BaseActivity() {
    private val missionText: TextViewFont get() = find(R.id.mission_text)
    private val vissionText: TextViewFont get() = find(R.id.vission_text)
    private val goalsText: TextViewFont get() = find(R.id.goals_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.about_us_incl_content)

        missionText.fromHtml(R.string.about_us_mission_text)
        missionText.movementMethod = LinkMovementMethod.getInstance()

        vissionText.fromHtml(R.string.about_us_mission_text)
        vissionText.movementMethod = LinkMovementMethod.getInstance()

        goalsText.fromHtml(R.string.about_us_goals_text)
        goalsText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_ABOUT_US_POSITION).isChecked = true
    }

}
