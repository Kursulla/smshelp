package com.eutechpro.smshelp.friends

import android.os.Bundle
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.SmsHelpApplication
import kotlinx.android.synthetic.main.foundation_friends_incl_content.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject


class FriendsActivity : BaseActivity(), AnkoLogger, Mvp.View {
    @Inject
    internal lateinit var presenter: Mvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.foundation_friends_incl_content)

        SmsHelpApplication.friendsDaggerComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBindView()
    }

    override fun drawFriends(friends: ArrayList<Friend>) {
        friends.forEach {
            val friendView = FriendView(this)
            friendView.load(it)
            container.addView(friendView)
        }
    }

    override fun showError(messageResId: Int, shouldKillTheActivity: Boolean) {
        toast(messageResId)
        if(shouldKillTheActivity){
            finish()
        }
    }

    override fun selectMenuItem() {
        navigationView.menu.getItem(BaseActivity.MENU_FRIENDS_POSITION).isChecked = true
    }

}
