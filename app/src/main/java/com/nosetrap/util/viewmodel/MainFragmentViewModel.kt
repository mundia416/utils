package com.nosetrap.util.viewmodel

import androidx.lifecycle.ViewModel
import com.nosetrap.util.data.CounterData

class MainFragmentViewModel : ViewModel() {

    fun addOneToCounter(counterData: CounterData): Int {
        val newValue = counterData.processCounterValue() + 1
        counterData.inputCounterValue(newValue)
        return newValue
    }

}