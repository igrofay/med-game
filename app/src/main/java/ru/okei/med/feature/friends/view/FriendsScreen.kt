package ru.okei.med.feature.friends.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.friends.model.FriendsState
import ru.okei.med.feature.friends.view_model.FriendsVM
import ru.okei.med.feature.widget.LoadingIndicator
import ru.okei.med.feature.widget.RetryRequest

@Composable
fun FriendsScreen(
    friendsVM: FriendsVM = hiltViewModel()
) {
    val state by remember { friendsVM.state }
    when(state){
        FriendsState.BadInternetConnection -> RetryRequest {
            friendsVM.onEvent(FriendsEvent.RetryRequest)
        }
        FriendsState.Loading -> LoadingIndicator()
    }
}