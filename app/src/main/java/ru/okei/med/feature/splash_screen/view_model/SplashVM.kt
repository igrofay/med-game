package ru.okei.med.feature.splash_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.auth.RestoreSessionUseCase
import ru.okei.med.feature.splash_screen.model.SplashState
import javax.inject.Inject


@HiltViewModel
class SplashVM @Inject constructor(
    private val restoreSessionUseCase: RestoreSessionUseCase
) : ViewModel(){
    private val _state = mutableStateOf<SplashState>(SplashState.Loading)

    val state : State<SplashState> get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            restoreSessionUseCase.execute()
                .onSuccess {
                    _state.value = SplashState.Success
                }
                .onFailure {
                    Log.e("It", it.toString())
                    when(it){
                        Errors.Auth.RefreshTokenNotAvailable ->{
                            withContext(Dispatchers.Main) {
                                _state.value = SplashState.Error
                            }
                        }
                        else -> Log.e("SplashVM",it.message.toString())
                    }
                }
        }
    }


}