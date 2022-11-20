package ru.okei.med.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.*

class Timer(second: Int) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val timeString = mutableStateOf("")
    init {
        scope.launch {
            for (i in second downTo 0){
                withContext(Dispatchers.Main){
                    timeString.value = String.format("%02d:%02d",i/60,i%60)
                }
                delay(1_000)
            }
            scope.cancel()
        }
    }

    @Composable
    fun rememberState() = remember{ timeString }
}