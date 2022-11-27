package ru.okei.med.feature.friends.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private var textForSearchUser = ""
    private var job: Job? = null

    init {
        job = viewModelScope.launch {
            getFriendsUseCase.execute().onSuccess { friends ->
                _state.value = FriendsState.FriendList(friends)
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
                        _state.value = FriendsState.FriendList(friends)
                    }.onFailure(::errorProcessing)
                }
            }
            is FriendsEvent.SearchUser -> {
                textForSearchUser = event.textForSearching
                _state.value = FriendsState.Loading
                viewModelScope.launch {
                    delay(500)
                    if(event.equals(textForSearchUser)){
                        job?.cancel()
                        job = launch {

                        }
                    }
                }
            }
        }
    }
}