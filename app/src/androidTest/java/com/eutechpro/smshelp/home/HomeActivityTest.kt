package com.eutechpro.smshelp.home

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import android.support.test.runner.AndroidJUnit4
import com.eutechpro.smshelp.DelayedActivityTestRule
import com.eutechpro.smshelp.R
import com.eutechpro.smshelp.SmsHelpApplication
import com.eutechpro.smshelp.home.di.HomeDaggerComponent
import com.eutechpro.smshelp.matchers.ImageViewMatcher.hasResource
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*


@Suppress("RedundantVisibilityModifier")
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    public var activityTestRule: DelayedActivityTestRule<HomeActivity> = DelayedActivityTestRule(
            HomeActivity::class.java,
            DelayedActivityTestRule.OnBeforeActivityLaunchedListener {
                val homeDaggerComponent = mock<HomeDaggerComponent>()
                whenever(homeDaggerComponent.getPresenter()).thenReturn(presenter)
                SmsHelpApplication.homeDaggerComponent = homeDaggerComponent
            })

    private val presenter = mock<Mvp.Presenter>()
    private lateinit var view: HomeActivity

    @Before
    fun setUp() {
        view = activityTestRule.launchActivity(Intent())
    }

    @Test
    fun testScheduling() {
        //Given
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val date = format.parse("27/04/1985") as Date

        //When
        runOnUiThread {
            view.setStatusScheduled(date)
        }

        //Then
        onView(withId(R.id.heart)).check(matches(hasResource(R.drawable.heart_full)))
        onView(withId(R.id.statusMessage)).check(matches(withText("Sledece slanje je \n27/April/1985")))
        onView(withId(R.id.statusDescription)).check(matches(withText(R.string.scheduled_status_description)))
        onView(withId(R.id.scheduleBtn)).check(matches(withText(R.string.scheduled_btn)))
    }

    @Test
    fun testUnScheduling() {
        //Given

        //When
        runOnUiThread {
            view.setStatusNotScheduled()
        }

        //Then
        onView(withId(R.id.heart)).check(matches(hasResource(R.drawable.heart_broken)))
        onView(withId(R.id.statusMessage)).check(matches(withText(R.string.not_scheduled_status_title)))
        onView(withId(R.id.statusDescription)).check(matches(withText(R.string.not_scheduled_status_description)))
        onView(withId(R.id.scheduleBtn)).check(matches(withText(R.string.not_scheduled_btn)))
    }
}