package ru.okei.med.feature.auth_screen.view_model

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.auth.SignInUseCase
import ru.okei.med.domain.use_case.auth.SignUpUseCase
import ru.okei.med.feature.auth_screen.model.AuthState
import ru.okei.med.feature.auth_screen.model.AuthEvent
import ru.okei.med.feature.base.EventBase
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel(), EventBase<AuthEvent> {
    private val _state = mutableStateOf<AuthState>(AuthState.FormInput())
    val state: State<AuthState> get() = _state

    private val _error: MutableState<Errors?> = mutableStateOf(null)
    val error: State<Errors?> get() = _error

    private var jobForCreatingSession: Job? = null

    override fun onEvent(event: AuthEvent) {
        when(event){
            AuthEvent.SignIn -> {
                signIn()
            }
            AuthEvent.SignUp -> {
                signUp()
            }
        }
    }
    private fun signIn(){
        jobForCreatingSession?.cancel()
        jobForCreatingSession = viewModelScope.launch(Dispatchers.IO) {
            val form = _state.value as AuthState.FormInput
            signInUseCase.execute(form.email, form.password)
                .onSuccess {
                    _state.value = AuthState.Success
                }.onFailure(::errorProcessing)
        }
    }
    private fun signUp(){
        jobForCreatingSession?.cancel()
        jobForCreatingSession = viewModelScope.launch(Dispatchers.IO) {
            val form = _state.value as AuthState.FormInput
            signUpUseCase.execute(form.email,form.nickname,form.password)
                .onSuccess {
                    _state.value = AuthState.Success
                }.onFailure(::errorProcessing)
        }
    }

    private fun errorProcessing(e:Throwable){
        when(e){
            is Errors.Auth.ErrorInEmail->{
                val form = _state.value as AuthState.FormInput
                form.emailIsError = true
                form.passwordIsError =false
                form.nicknameIsError= false
                _error.value = e
            }
            is Errors.Auth.ErrorInPassword->{
                val form = _state.value as AuthState.FormInput
                form.emailIsError = false
                form.passwordIsError =true
                form.nicknameIsError= false
                _error.value = e
            }
            is Errors.Auth.ErrorInName -> {
                val form = _state.value as AuthState.FormInput
                form.emailIsError = false
                form.nicknameIsError= true
                form.passwordIsError =false
                _error.value = e
            }
            is Errors.Request.NetworkError->{
                _error.value = e
            }
            else->{
                Log.e("AuthVM",e.message.toString())
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        jobForCreatingSession?.cancel()
    }
}