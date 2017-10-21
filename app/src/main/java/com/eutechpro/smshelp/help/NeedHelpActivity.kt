package com.eutechpro.smshelp.help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.fromHtml
import kotlinx.android.synthetic.main.need_help_incl_content.*


class NeedHelpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.need_help_incl_content)

        description.fromHtml(R.string.need_help_description)
        description.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_NEED_HELP_POSITION).isChecked = true
    }
}
