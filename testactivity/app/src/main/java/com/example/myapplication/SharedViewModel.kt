package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel: ViewModel(){
    private val _counter = MutableStateFlow(0)
    val SharedViewModel: StateFlow<Int> = _counter
    fun counter(){
        _counter.value++
    }


}