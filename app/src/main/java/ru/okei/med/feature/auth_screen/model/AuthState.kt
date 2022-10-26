package ru.okei.med.feature.auth_screen.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


sealed class AuthState{
    class FormInput(email:String = "", nickname:String ="", password:String =""): AuthState() {
        var email by mutableStateOf(email)
        var emailIsError by mutableStateOf(false)
        var nickname by mutableStateOf(nickname)
        var nicknameIsError by mutableStateOf(false)
        var password by mutableStateOf(password)
        var passwordIsError by mutableStateOf(false)
    }
    object Success : AuthState()
}