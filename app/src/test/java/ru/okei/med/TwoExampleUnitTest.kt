package ru.okei.med

import org.junit.Test

class TwoExampleUnitTest {
    @Test
    fun formToF(){
        val urlIcon = "//cdn.weatherapi.com/weather/64x64/day/326.png"
            .split("64x64").joinToString("128x128")
        println("https:${urlIcon}")
    }
}