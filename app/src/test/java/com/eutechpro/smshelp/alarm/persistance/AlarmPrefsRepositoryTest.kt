package com.eutechpro.smshelp.alarm.persistance

import android.content.Context
import android.content.SharedPreferences
import com.eutechpro.smshelp.BuildConfig
import com.eutechpro.smshelp.TestingApplication
import com.eutechpro.smshelp.sms.Sms
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        application = TestingApplication::class
)
class AlarmPrefsRepositoryTest {
    private val SMS_NUMBER = 111
    private val SMS_DATE = Date()
    private val SMS_MESSAGE = "SMS_MESSAGE_TEST"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmRepository: AlarmRepository
    private var testSubscriber = TestSubscriber<Sms>()

    @Before
    fun setUp() {
        sharedPreferences = RuntimeEnvironment.application.getSharedPreferences("test", Context.MODE_PRIVATE)
        alarmRepository = AlarmPrefsRepository(sharedPreferences)
    }


    @Test
    fun storeNextAlarmSms() {

    }


    @Test
    fun fetchNextSmsWhenThereIsSmsStored() {
        //Given
        val sms = Sms(SMS_NUMBER, SMS_DATE, SMS_MESSAGE)
        storeSmsJustForTest(sms)

        //When
        alarmRepository.fetchNextSms(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent(1000, TimeUnit.MILLISECONDS)

        //Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assertEquals(sms, testSubscriber.onNextEvents[0])
    }

    @Test
    fun fetchNextSmsWhenThereIsNOSmsStored() {
        //Given
        alarmRepository.fetchNextSms(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent(1000, TimeUnit.MILLISECONDS)

        //Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assertNull(testSubscriber.onNextEvents[0])
    }

    @Test
    fun removeSmsAlarmFromStorage() {
        //Given
        val sms = Sms(SMS_NUMBER, SMS_DATE, SMS_MESSAGE)
        storeSmsJustForTest(sms)
        val testSubscriber = TestSubscriber<Boolean>()

        //When
        alarmRepository.removeSmsAlarmFromStorage(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent(1000, TimeUnit.MILLISECONDS)

        //Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assertTrue(testSubscriber.onNextEvents[0])

    }

    @After
    fun tearDown() {
        removeSmsJustForTest(SMS_NUMBER)
    }


    /*
    Ugly dependant test preparation
     */
    private fun storeSmsJustForTest(sms: Sms) {
        sharedPreferences.edit()
                .putInt("SMS_NUMBER" + sms.number, sms.number)
                .putString("SMS_MESSAGE" + sms.number, sms.message)
                .putLong("SMS_DATE" + sms.number, sms.date.time)
                .commit()
    }

    private fun removeSmsJustForTest(smsNumber: Int) {
        sharedPreferences.edit()
                .remove("SMS_NUMBER" + smsNumber)
                .remove("SMS_MESSAGE" + smsNumber)
                .remove("SMS_DATE" + smsNumber)
                .commit()
    }

}