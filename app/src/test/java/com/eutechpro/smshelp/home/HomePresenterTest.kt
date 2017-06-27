package com.eutechpro.smshelp.home

import com.eutechpro.smshelp.sms.Sms
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import rx.Observable


class HomePresenterTest {
    private val modelMock = mock<Mvp.Model>()
    private val viewMock = mock<Mvp.View>()
    private val smsMock = mock<Sms>()
    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(modelMock.getNextScheduledSmsStream()).thenReturn(Observable.just(smsMock))

        presenter = Presenter(modelMock)
    }

    @Test
    fun testCheckingIsScheduled_OnBinding() {

        presenter.bindView(viewMock)

        verify(modelMock).getNextScheduledSmsStream()
        verify(viewMock).setStatusScheduled(anyOrNull())
        verify(viewMock, times(0)).setStatusNotScheduled()
        verify(viewMock, times(0)).showError(any())
    }


    @Test
    fun testCheckingStatus_Scheduled() {
        whenever(modelMock.getNextScheduledSmsStream()).thenReturn(Observable.just(smsMock))

        presenter.bindView(viewMock)

        verify(viewMock, times(1)).setStatusScheduled(anyOrNull())
        verify(viewMock, times(0)).setStatusNotScheduled()
        verify(viewMock, times(0)).showError(any())
    }

    @Test
    fun testCheckingStatus_NotScheduled() {
        whenever(modelMock.getNextScheduledSmsStream()).thenReturn(Observable.just(null))

        presenter.bindView(viewMock)

        verify(viewMock, times(0)).setStatusScheduled(anyOrNull())
        verify(viewMock, times(1)).setStatusNotScheduled()
        verify(viewMock, times(0)).showError(any())
    }
}