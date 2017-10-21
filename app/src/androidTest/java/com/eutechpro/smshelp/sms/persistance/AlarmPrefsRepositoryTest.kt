package com.eutechpro.smshelp.sms.persistance

import android.content.Context
import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.runner.AndroidJUnit4
import com.eutechpro.smshelp.sms.Sms
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AlarmPrefsRepositoryTest {
    private val SMS_NUMBER = 111
    private val SMS_DATE = Date()
    private val SMS_MESSAGE = "SMS_MESSAGE_TEST"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmRepository: SmsRepository
    private var testSubscriber = TestSubscriber<Sms>()

    @Before
    fun setUp() {
        val context = getInstrumentation().targetContext
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        alarmRepository = SmsPrefsRepository(sharedPreferences)
    }


    @Test
    fun fetchNextSmsWhenThereIsSmsStored() {
        //Given
        val sms = Sms(SMS_NUMBER, SMS_DATE, SMS_MESSAGE)
        storeSmsJustForTest(sms)

        //When
        alarmRepository.fetchNextSms(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitValueCount(1, 500, TimeUnit.MILLISECONDS)

        //Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assertEquals(sms, testSubscriber.onNextEvents[0])
    }

    @Test
    fun fetchNextSmsWhenThereIsNoSmsStored() {
        //Given
        alarmRepository.fetchNextSms(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitValueCount(1, 500, TimeUnit.MILLISECONDS)

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
        alarmRepository.removeSms(SMS_NUMBER).subscribe(testSubscriber)
        testSubscriber.awaitValueCount(1, 500, TimeUnit.MILLISECONDS)

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
                .putInt("SMS_NUMBER_KEY" + sms.number, sms.number)
                .putString("SMS_MESSAGE" + sms.number, sms.message)
                .putLong("SMS_DATE" + sms.number, sms.date.time)
                .commit()
    }

    private fun removeSmsJustForTest(smsNumber: Int) {
        sharedPreferences.edit()
                .remove("SMS_NUMBER_KEY" + smsNumber)
                .remove("SMS_MESSAGE" + smsNumber)
                .remove("SMS_DATE" + smsNumber)
                .commit()
    }

}