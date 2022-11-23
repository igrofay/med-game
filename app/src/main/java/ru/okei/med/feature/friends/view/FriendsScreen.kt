package ru.okei.med.feature.friends.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.friends.view_model.FriendsVM

@Composable
fun FriendsScreen(
    friendsVM: FriendsVM = hiltViewModel()
) {
}