package com.eutechpro.smshelp.need_help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.custom_views.TextViewFont
import com.eutechpro.smshelp.extensions.fromHtml
import org.jetbrains.anko.find


class NeedHelpActivity : BaseActivity() {
    private val description: TextViewFont
        get() = find(R.id.description)

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
