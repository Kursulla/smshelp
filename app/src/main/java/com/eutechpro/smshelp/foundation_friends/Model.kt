package com.eutechpro.smshelp.foundation_friends

import android.content.res.AssetManager
import com.eutechpro.smshelp.extensions.loadJsonDataFromAssets
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jetbrains.anko.AnkoLogger
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*


class Model(val assets: AssetManager) : Mvp.Model, AnkoLogger {
    override fun fetchListOfFoundationFriends(): Observable<ArrayList<Friend>> {
        return Observable.create(Observable.OnSubscribe<ArrayList<Friend>> {
            subscriber ->
            val friendsJson = assets.loadJsonDataFromAssets("friends.json")
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(List::class.java, Friend::class.java!!)
            val adapter: JsonAdapter<List<Friend>> = moshi.adapter(type)
            val friends: ArrayList<Friend> = adapter.fromJson(friendsJson) as ArrayList<Friend>
            subscriber?.onNext(friends)
        }).subscribeOn(Schedulers.io())


    }

}