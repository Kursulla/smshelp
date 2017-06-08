package com.eutechpro.smshelp.foundation_friends

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.custom_views.TextViewFont
import com.eutechpro.smshelp.extensions.load
import org.jetbrains.anko.find

class FriendView : LinearLayout {
    private val friendImage: ImageView get() = find(R.id.friend_image)
    private val friendName: TextViewFont get() = find(R.id.friend_name)

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
        friendImage.setImageResource(R.mipmap.ic_launcher)//todo change to placeholder

    }

    fun load(friend: Friend) {
        friendName.text = friend.name.replace(" ","\n")
        friendImage.load(friend.imageUrl)
    }
}