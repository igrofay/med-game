package ru.okei.med.feature.friends.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.friends.model.FriendsState
import javax.inject.Inject

@HiltViewModel
class FriendsVM @Inject constructor(

): ViewModel(), EventBase<FriendsEvent>{
    private val _state = mutableStateOf<FriendsState>(FriendsState.Loading)
    val state: State<FriendsState> get() = _state

    override fun onEvent(event: FriendsEvent) {
        when(event){
            FriendsEvent.RetryRequest ->{}
        }
    }
}