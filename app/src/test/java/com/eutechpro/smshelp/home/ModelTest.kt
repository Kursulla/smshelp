package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.scheduler.SendingSmsScheduler
import com.eutechpro.smshelp.sms.Sms
import com.eutechpro.smshelp.sms.persistance.SmsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.observers.TestSubscriber
import java.util.*
import java.util.concurrent.TimeUnit


class ModelTest {
    private val mockSmsScheduler = mock<SendingSmsScheduler>()
    private val mockSmsRepository = mock<SmsRepository>()
    private val testSubscriber = TestSubscriber<Sms>()
    private val mockSms = mock<Sms>()
    private val model = Model(mockSmsScheduler, mockSmsRepository)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        model.getNextScheduledSmsStream().subscribe(testSubscriber)
    }


    @Test
    fun testCheckStatusWhenSmsScheduled() {
        whenever(mockSmsRepository.fetchNextSms(any())).thenReturn(Observable.just(mockSms))

        model.checkStatus()
        testSubscriber.awaitValueCount(1, 500, TimeUnit.MILLISECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        assertEquals(mockSms,testSubscriber.onNextEvents[0])
    }
    @Test
    fun testCheckStatusWhenSmsNotScheduled() {
        whenever(mockSmsRepository.fetchNextSms(any())).thenReturn(Observable.just(null))

        model.checkStatus()

        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        assertEquals(null,testSubscriber.onNextEvents[0])
    }

    @Test
    fun testScheduling_OK() {
        whenever(mockSmsRepository.storeNextSms(any())).thenReturn(Observable.just(true))
        val currentDate = Date()
        model.schedule(currentDate)

        verify(mockSmsScheduler).scheduleNextSms(any())
        testSubscriber.assertNoErrors()
        testSubscriber.assertNotCompleted()
        val smsFromStream = testSubscriber.onNextEvents[0]
        assertNotNull(smsFromStream)
        assertEquals(smsFromStream.date, currentDate)
    }
    @Test
    fun testScheduling_error(){
        whenever(mockSmsRepository.storeNextSms(any())).thenReturn(Observable.just(false))

        model.schedule(Date())

        testSubscriber.assertNotCompleted()
        testSubscriber.assertError(Model.SchedulingException::class.java)

    }
    @Test
    fun testUnScheduling_OK() {
        whenever(mockSmsRepository.removeSms(any())).thenReturn(Observable.just(true))

        model.unSchedule()

        verify(mockSmsScheduler).unscheduleNextSms(any())
        testSubscriber.assertNotCompleted()
        testSubscriber.assertNoErrors()
        assertNull(testSubscriber.onNextEvents[0])
    }

    @Test
    fun testUnScheduling_error() {
        whenever(mockSmsRepository.removeSms(any())).thenReturn(Observable.just(false))

        model.unSchedule()

        verify(mockSmsScheduler).unscheduleNextSms(any())
        testSubscriber.assertNotCompleted()
        testSubscriber.assertError(Model.SchedulingException::class.java)
    }
}