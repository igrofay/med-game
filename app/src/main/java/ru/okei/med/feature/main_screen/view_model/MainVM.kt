package ru.okei.med.feature.main_screen.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.profile.GetProfileUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.main_screen.model.MainEvent
import ru.okei.med.feature.main_screen.model.MainState
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
): ViewModel(), EventBase<MainEvent> {
    private val _state = mutableStateOf(MainState())
    val state: State<MainState> get() = _state

    private val _error: MutableState<Errors?> = mutableStateOf(null)
    val error: State<Errors?> get() = _error

    init {
        viewModelScope.launch {
            getProfileUseCase.execute()
                .onSuccess { profile->
                    _state.value = MainState(profileBody = profile)
                }.onFailure { e ->
                    if(e is Errors){
                        _error.value = e
                    }
                }
        }
    }

    override fun onEvent(event: MainEvent) {
        when(event){
            MainEvent.UpdateData -> {
                viewModelScope.launch {
                    getProfileUseCase.execute()
                        .onSuccess { profile->
                            _state.value = MainState(profileBody = profile)
                        }.onFailure { e ->
                            if(e is Errors){ _error.value = e }
                        }
                }
            }
        }
    }
}