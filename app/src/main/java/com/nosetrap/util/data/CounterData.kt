package com.nosetrap.util.data

open class CounterData(){

    private var currentCounterValue: Int = 0


    fun inputCounterValue(value: Int){
        this.currentCounterValue = value
    }

    open fun processCounterValue(): Int{
        return currentCounterValue
    }
}