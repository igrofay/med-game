package ru.okei.med.feature.friends.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.frends.GetFriendsUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.friends.model.FriendsState
import javax.inject.Inject

@HiltViewModel
class FriendsVM @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase
): ViewModel(), EventBase<FriendsEvent>{
    private val _state = mutableStateOf<FriendsState>(FriendsState.Loading)
    val state: State<FriendsState> get() = _state

    init {
        viewModelScope.launch {
            getFriendsUseCase.execute().onSuccess { friends ->
                _state.value = FriendsState.Success(friends)
            }.onFailure(::errorProcessing)
        }
    }

    private fun errorProcessing(e:Throwable){
        when(e){
            is Errors.Request.NetworkError->{
                _state.value = FriendsState.BadInternetConnection
            }
            else ->{
                Log.e("FriendsVM",e.message.toString())
            }
        }
    }

    override fun onEvent(event: FriendsEvent) {
        when(event){
            FriendsEvent.RetryRequest ->{
                viewModelScope.launch {
                    getFriendsUseCase.execute().onSuccess { friends ->
                        _state.value = FriendsState.Success(friends)
                    }.onFailure(::errorProcessing)
                }
            }
        }
    }
}