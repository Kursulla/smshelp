package com.eutechpro.smshelp.volunteer

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.fromHtml
import kotlinx.android.synthetic.main.become_volunteer_incl_content.*

class BecomeVolunteerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.become_volunteer_incl_content)

        description.fromHtml(R.string.become_volunteer_description)
        description.movementMethod = LinkMovementMethod.getInstance()

        moto.fromHtml(R.string.become_volunteer_moto)
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_BECOME_VOLUNTEER_POSITION).isChecked = true
    }

}
