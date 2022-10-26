package ru.okei.med.feature.splash_screen.model

sealed class SplashState {
    object Loading: SplashState()
    object Success: SplashState()
    object Error: SplashState()
}