package com.eutechpro.smshelp.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.fromHtml
import kotlinx.android.synthetic.main.about_us_incl_content.*


class AboutUsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.about_us_incl_content)

        missionText.fromHtml(R.string.about_us_mission_text)
        missionText.movementMethod = LinkMovementMethod.getInstance()

        vissionText.fromHtml(R.string.about_us_vission_text)
        vissionText.movementMethod = LinkMovementMethod.getInstance()

        goalsText.fromHtml(R.string.about_us_goals_text)
        goalsText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_ABOUT_US_POSITION).isChecked = true
    }

}
