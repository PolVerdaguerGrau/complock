package com.example.androiddevchallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScreenViewModel : ViewModel() {
    var timePassed by mutableStateOf(0)
    var halfTimePassed by mutableStateOf(0)
    var timestamp: Long by mutableStateOf(0)
    var halfTimestamp: Long by mutableStateOf(0)

    var timerValue by mutableStateOf(0)
    var maxValue by mutableStateOf(0)

    fun clear() {
        maxValue = 0
        timestamp = 0
        halfTimestamp = 0
        timePassed = 0
        halfTimePassed = 0
        timerValue = 0
    }
}