package com.eutechpro.smshelp.friends

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.extensions.load
import kotlinx.android.synthetic.main.freinds_view.view.*

class FriendView : LinearLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.freinds_view, this)
        friendImage.setImageResource(R.drawable.friends_place_holder)

    }

    fun load(friend: Friend) {
        friendName.text = friend.name.replace(" ","\n")
        friendImage.load(friend.imageUrl)
    }
}