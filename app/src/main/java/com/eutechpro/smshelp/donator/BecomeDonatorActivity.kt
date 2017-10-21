package com.eutechpro.smshelp.donator

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.fromHtml
import kotlinx.android.synthetic.main.become_donator_incl_content.*


class BecomeDonatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.become_donator_incl_content)

        description.fromHtml(R.string.become_donator_description)
        description.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_BECOME_DONATOR_POSITION).isChecked = true
    }

}
