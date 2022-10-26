package ru.okei.med.feature.auth_screen.model

sealed class AuthEvent {
    object SignUp : AuthEvent()
    object SignIn: AuthEvent()
}