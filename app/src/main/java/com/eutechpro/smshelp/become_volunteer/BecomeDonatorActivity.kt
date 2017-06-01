package com.eutechpro.smshelp.become_volunteer

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.custom_views.TextViewFont
import com.eutechpro.smshelp.extensions.fromHtml
import org.jetbrains.anko.find


class BecomeDonatorActivity : BaseActivity() {
    private val description: TextViewFont
        get() = find(R.id.description)

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
