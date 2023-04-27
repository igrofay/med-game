package ru.okei.med.feature.main_screen.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.battle.RequestFightRequestsInteractor
import ru.okei.med.domain.use_case.profile.GetProfileUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.main_screen.model.MainEvent
import ru.okei.med.feature.main_screen.model.MainSideEffect
import ru.okei.med.feature.main_screen.model.MainState
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val requestFightRequestsInteractor : RequestFightRequestsInteractor,
): ViewModel(), EventBase<MainEvent> {
    private val _state = mutableStateOf(MainState())
    val state: State<MainState> get() = _state
    private var subscription : Job? = null


    private val _sideEffect = mutableStateOf<MainSideEffect?>(null)
    val sideEffect : State<MainSideEffect?> get() = _sideEffect

    init {
        viewModelScope.launch {
            getProfileUseCase.execute()
                .onSuccess { profile->
                    _state.value = MainState(profileBody = profile)
                }.onFailure { e ->
                    if(e is Errors){
                        e.message?.let {
                            _sideEffect.value = MainSideEffect.ShowMessage(it)
                        }
                    }
                }
        }
        subscription = viewModelScope.launch {
            requestFightRequestsInteractor.executeFlow().collect{
                _state.value = _state.value.copy(requestForFight = it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.cancel()
    }


    override fun onEvent(event: MainEvent) {
        when(event){
            MainEvent.UpdateData -> {
                viewModelScope.launch {
                    getProfileUseCase.execute()
                        .onSuccess { profile->
                            _state.value = MainState(profileBody = profile)
                        }.onFailure { e ->
                            if(e is Errors){ e.message?.let {
                                _sideEffect.value = MainSideEffect.ShowMessage(it)
                            } }
                        }
                }
            }
            MainEvent.ClearSideEffect -> {
                _sideEffect.value = null
            }
            MainEvent.RefuseToFight -> {
                _state.value.requestForFight?.tokenRoom?.let {
                    try {
                        viewModelScope.launch {
                            requestFightRequestsInteractor.refuse(it)
                        }
                    }catch (_: Throwable){

                    }
                }
            }
            MainEvent.AcceptFight -> {
                _state.value.requestForFight?.tokenRoom?.let {
                    _sideEffect.value = MainSideEffect.GoToFightWithFriend(it)
                }
            }
        }
    }


}