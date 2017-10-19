package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.alarm.SmsScheduler
import com.eutechpro.smshelp.sms.Sms
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.observers.TestSubscriber
import java.util.concurrent.TimeUnit


class ModelTest {
    private val mockSmsScheduler = mock<SmsScheduler>()
    private val testSubscriber = TestSubscriber<Sms>()
    private val mockSms = mock<Sms>()
    private val model = Model(mockSmsScheduler)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        model.getNextScheduledSmsStream().subscribe(testSubscriber)
    }


    @Test
    fun testCheckStatusWhenSmsScheduled() {
        whenever(mockSmsScheduler.getNextScheduledSms(any())).thenReturn(Observable.just(mockSms))

        model.checkStatus()
        testSubscriber.awaitTerminalEvent(1000,TimeUnit.MILLISECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        assertEquals(mockSms,testSubscriber.onNextEvents[0])
    }
    @Test
    fun testCheckStatusWhenSmsNotScheduled() {
        whenever(mockSmsScheduler.getNextScheduledSms(any())).thenReturn(Observable.just(null))

        model.checkStatus()

        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        assertEquals(null,testSubscriber.onNextEvents[0])
    }

    @Test
    fun testSuccessfullScheduling() {
        whenever(mockSmsScheduler.scheduleNextSms(any())).thenReturn(Observable.just(true))

        model.schedule(any())

        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        assertNotNull(testSubscriber.onNextEvents[0])

    }
    @Test
    fun testUnsuccesfullScheduling(){
        whenever(mockSmsScheduler.scheduleNextSms(any())).thenReturn(Observable.just(false))

        model.schedule(any())

        testSubscriber.assertNotCompleted()
        testSubscriber.assertError(Model.SchedulingException::class.java)

    }
    @Test
    fun testSuccessfullInScheduling() {
        whenever(mockSmsScheduler.unscheduleNextSms(any())).thenReturn(Observable.just(true))

        model.unSchedule()

        testSubscriber.assertNotCompleted()
        testSubscriber.assertNoErrors()
        assertNull(testSubscriber.onNextEvents[0])
    }

    @Test
    fun testUnSuccessfullInScheduling() {
        whenever(mockSmsScheduler.unscheduleNextSms(any())).thenReturn(Observable.just(false))

        model.unSchedule()

        testSubscriber.assertNotCompleted()
        testSubscriber.assertError(Model.SchedulingException::class.java)
    }
}