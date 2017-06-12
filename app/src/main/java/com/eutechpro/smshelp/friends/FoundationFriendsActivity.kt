package com.eutechpro.smshelp.volunteer

import android.os.Bundle
import com.eutechpro.smshelp.BaseActivity
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.friends.*
import com.google.android.flexbox.FlexboxLayout
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*


class FoundationFriendsActivity : BaseActivity(), AnkoLogger, Mvp.View {
    private val container: FlexboxLayout get() = find(R.id.container)
    private val presenter: Mvp.Presenter get() = Presenter(Model(assets))//todo inject this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(R.layout.foundation_friends_incl_content)
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
