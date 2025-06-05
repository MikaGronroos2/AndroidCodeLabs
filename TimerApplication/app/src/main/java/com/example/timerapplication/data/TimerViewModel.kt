package com.example.timerapplication.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    var timerLabel: MutableState<String> = mutableStateOf("Ready")
    var timerValue: MutableState<Int> = mutableIntStateOf(0)
    var timerIsRunning: MutableState<Boolean> = mutableStateOf(false)

    fun timerCountDown(seconds: Int) {
        viewModelScope.launch {
            timerIsRunning.value = true

            for (i in seconds downTo 0) {
                timerValue.value = i
                timerLabel.value = "$i seconds remaining"
                delay(1000L)
            }
            timerLabel.value = "Done"
            timerIsRunning.value = false
        }
    }
}