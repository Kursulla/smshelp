package com.eutechpro.smshelp

import org.junit.After
import org.junit.Before
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.schedulers.Schedulers

open class BaseRxTest {
    @Before
    open fun setUp() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
            override fun getMainThreadScheduler(): Scheduler {
                return Schedulers.immediate()
            }
        })
    }

    @After
    open fun tearDown() {
        RxAndroidPlugins.getInstance().reset()
    }
}