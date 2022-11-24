package ru.okei.med.feature.main_screen.model


sealed class MainEvent {
    object UpdateData : MainEvent()
}