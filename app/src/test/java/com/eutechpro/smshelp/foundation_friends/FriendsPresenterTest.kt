package com.eutechpro.smshelp.foundation_friends

import com.eutechpro.smshelp.BaseRxTest
import com.eutechpro.smshelp.R
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import rx.Observable
import java.util.*


class FriendsPresenterTest : BaseRxTest() {
    private val modelMock = mock<Mvp.Model>()
    private val viewMock = mock<Mvp.View>()
    private var presenter: Presenter? = null
    private lateinit var spiedPresenter: Presenter

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        presenter = Presenter(modelMock)
        spiedPresenter = spy(presenter)!!

    }

    @Test
    fun testSubscription() {
        //Given
        mockModelToReturnListOfFreinds()

        //When
        spiedPresenter.bindView(viewMock)


        //Then
        verify(spiedPresenter).add(any())
    }


    @Test
    fun testUnbinding() {
        spiedPresenter.unBindView()

        verify(spiedPresenter).clearSubscriptions()
    }

    @Test
    fun testDrawingFetchedFriends() {
        //Given
        val arr = mockModelToReturnListOfFreinds()

        //When
        spiedPresenter.bindView(viewMock)

        //Then
        verify(viewMock).drawFriends(arr as ArrayList<Friend>)
    }

    @Test
    fun testHandlingBadLoadingOfFreinds() {
        //Given
        whenever(modelMock.fetchListOfFoundationFriends()).thenReturn(Observable.just(null))

        //When
        spiedPresenter.bindView(viewMock)

        //Then
        verify(viewMock).showError(R.string.foundation_friends_error, true)
    }

    @Test
    fun testHandlingLoadingEmptyArrayOfFreinds() {
        //Given
        whenever(modelMock.fetchListOfFoundationFriends()).thenReturn(Observable.just(ArrayList<Friend>()))

        //When
        spiedPresenter.bindView(viewMock)

        //Then
        verify(viewMock).showError(R.string.foundation_friends_error, true)
    }

    private fun mockModelToReturnListOfFreinds(): List<Friend> {
        val arr = ArrayList<Friend>()
        arr.add(mock<Friend>())
        whenever(modelMock.fetchListOfFoundationFriends()).thenReturn(Observable.just(arr))

        return arr
    }
}