package com.eutechpro.smshelp.sms

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber
import java.util.*

@RunWith(AndroidJUnit4::class)
class SmsPrefsRepositoryTest {
    private lateinit var smsRepository: SmsRepository

    private lateinit var statusTestSubscriber: TestSubscriber<Boolean>
    private lateinit var smsTestSubscriber: TestSubscriber<Sms>

    @Before
    fun setUp() {
        smsRepository = SmsPrefsRepository(InstrumentationRegistry.getTargetContext())
        statusTestSubscriber = TestSubscriber<Boolean>()
        smsTestSubscriber = TestSubscriber<Sms>()

    }

    @Test
    fun testStoringAndFetching() {
        //given
        val date = Date()
        val sms = Sms(222, date, "test_message")

        //When
        smsRepository.storeLastSms(sms).subscribe(statusTestSubscriber)

        //Then
        statusTestSubscriber.assertNoErrors()
        statusTestSubscriber.assertCompleted()
        val storedFlag: Boolean = statusTestSubscriber.onNextEvents[0]
        assertTrue("Lools like not stored as expected", storedFlag)


        //When
        smsRepository.fetchLastSms().subscribe(smsTestSubscriber)

        //Then
        smsTestSubscriber.assertNoErrors()
        smsTestSubscriber.assertCompleted()

        val storedSms: Sms = smsTestSubscriber.onNextEvents[0]
        assertNotNull(storedSms)
        assertEquals(222, storedSms.number)
        assertEquals("test_message", storedSms.message)
        assertEquals(date, storedSms.date)

    }


    @Test
    fun getTotalDonatedMoney() {
//todo add this
    }

}