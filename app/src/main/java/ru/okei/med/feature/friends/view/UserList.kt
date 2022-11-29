package ru.okei.med.feature.friends.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.feature.friends.model.FriendsEvent

@Composable
fun UserList(
    userList: List<FriendInfo>,
    action: (FriendsEvent)->Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(
            items = userList
        ){ friendInfo ->
            ItemFriendInfo(friendInfo,action= action)
        }
    }
}