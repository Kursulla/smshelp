package com.eutechpro.smshelp.home

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import rx.Observable
import java.util.*


class HomePresenterTest {
    private val modelMock = mock<Mvp.Model>()
    private val viewMock = mock<Mvp.View>()
    private var presenter: Presenter? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(modelMock.getNextScheduledDateStream()).thenReturn(Observable.just(Date()))

        presenter = Presenter(modelMock)
    }

    @Test
    fun testCheckingIsScheduled_OnBinding() {
        whenever(modelMock.getIsAlarmScheduledStream()).thenReturn(Observable.just(true))

        presenter?.bindView(viewMock)

        verify(modelMock).getIsAlarmScheduledStream()
        verify(viewMock, times(0)).showError(any())
    }

    @Test
    fun testDrawing_ScheduledStatus() {
        whenever(modelMock.getIsAlarmScheduledStream()).thenReturn(Observable.just(true))

        presenter?.bindView(viewMock)

        verify(viewMock).setStatusScheduled(any())
        verify(viewMock, times(0)).setStatusNotScheduled()
        verify(viewMock, times(0)).showError(any())
    }

    @Test
    fun testDrawing_NotScheduledStatus() {
        whenever(modelMock.getIsAlarmScheduledStream()).thenReturn(Observable.just(false))

        presenter?.bindView(viewMock)

        verify(viewMock, times(1)).setStatusNotScheduled()
        verify(viewMock, times(0)).setStatusScheduled(any())
        verify(viewMock, times(0)).showError(any())
    }
}