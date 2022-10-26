package ru.okei.med.feature.base

interface EventBase<T> {
    fun onEvent(event: T)
}