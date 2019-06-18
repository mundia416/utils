package com.nosetrap.util

import com.google.common.truth.Truth.assertThat
import com.nosetrap.util.data.CounterData
import com.nosetrap.util.viewmodel.MainFragmentViewModel
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainViewModelTest {

    val viewModel = MainFragmentViewModel()

    val mockCounterData = mock(CounterData::class.java)


    @Test
    fun addOneToCurrentValueOfCounter(){
        `when`(mockCounterData.processCounterValue()).thenReturn(5)

        val addedCounterValue: Int = viewModel.addOneToCounter(mockCounterData)
       assertThat(addedCounterValue).isEqualTo(6)
    }
}