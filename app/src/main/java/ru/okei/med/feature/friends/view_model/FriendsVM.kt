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
import ru.okei.med.domain.use_case.frends.ActionOnFriendUseCase
import ru.okei.med.domain.use_case.frends.GetFriendsUseCase
import ru.okei.med.domain.use_case.frends.GetUserWithSuchNameUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.friends.model.FriendsState
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@HiltViewModel
class FriendsVM @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getUserWithSuchNameUseCase: GetUserWithSuchNameUseCase,
    private val actionOnFriendUseCase: ActionOnFriendUseCase,
): ViewModel(), EventBase<FriendsEvent>{
    private val _state = mutableStateOf<FriendsState>(FriendsState.Loading)
    val state: State<FriendsState> get() = _state
    private var searchUsersText = AtomicReference("")
    private var job: Job? = null


    init {
        getFriendList()
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
                _state.value = FriendsState.Loading
                searchUsers(searchUsersText.get())
            }
            is FriendsEvent.SearchUser -> {
                _state.value = FriendsState.Loading
                searchUsers(event.textForSearching)
            }
            is FriendsEvent.AcceptFriendRequest -> {
                viewModelScope.launch {
                    actionOnFriendUseCase
                        .execute(event.email,ActionOnFriendUseCase.Action.AcceptRequest)
                        .onSuccess {

                            onEvent(FriendsEvent.RetryRequest)
                        }
                }
            }
            is FriendsEvent.CanselFriendRequest -> {
                viewModelScope.launch {
                    actionOnFriendUseCase
                        .execute(event.email,ActionOnFriendUseCase.Action.CancelRequest)
                        .onSuccess { onEvent(FriendsEvent.RetryRequest) }
                }
            }
            is FriendsEvent.DeleteFriend -> {
                viewModelScope.launch {
                    actionOnFriendUseCase
                        .execute(event.email,ActionOnFriendUseCase.Action.Delete)
                        .onSuccess { onEvent(FriendsEvent.RetryRequest) }
                }
            }
            is FriendsEvent.SendFriendRequest ->{
                viewModelScope.launch{
                    actionOnFriendUseCase
                        .execute(event.email,ActionOnFriendUseCase.Action.SendRequest)
                        .onSuccess { onEvent(FriendsEvent.RetryRequest) }
                }
            }
            FriendsEvent.GetFriendList -> getFriendList()
        }
    }

    private fun searchUsers(textForSearching: String){
        searchUsersText.set(textForSearching)
        viewModelScope.launch {
            delay(500)
            if(textForSearching == searchUsersText.get()){
                if(textForSearching.isNotBlank()){
                    job?.cancel()
                    job = launch {
                        getUserWithSuchNameUseCase.execute(textForSearching).onSuccess { users ->
                            _state.value = FriendsState.FoundUserList(users)
                        }.onFailure(::errorProcessing)
                    }
                }else{
                    getFriendList()
                }
            }
        }
    }

    private fun getFriendList(){
        job?.cancel()
        job = viewModelScope.launch {
            while (true){
                getFriendsUseCase.execute().onSuccess { friends ->
                    _state.value = FriendsState.FriendList(friends)
                }.onFailure(::errorProcessing)
                delay(3000L)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchUsersText.set(null)
    }
}