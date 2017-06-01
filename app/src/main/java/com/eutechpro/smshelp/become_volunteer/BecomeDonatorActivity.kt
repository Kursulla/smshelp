package com.eutechpro.smshelp.become_volunteer

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.custom_views.TextViewFont
import com.eutechpro.smshelp.extensions.fromHtml
import org.jetbrains.anko.find


class BecomeDonatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_activity)
        setSupportActionBar(find(R.id.toolbar))
        initDrawer()
        inflateContentLayout(R.layout.become_donator_incl_content)

        val description = find<TextViewFont>(R.id.description)
        description.fromHtml(R.string.become_donator_description)
        description.movementMethod = LinkMovementMethod.getInstance()
    }
}