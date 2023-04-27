package ru.okei.med.feature.friends.view

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.friends.model.FriendsSideEffect
import ru.okei.med.feature.friends.model.FriendsState
import ru.okei.med.feature.friends.view_model.FriendsVM
import ru.okei.med.feature.theme.DenseBlue
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.LoadingIndicator
import ru.okei.med.feature.widget.RetryRequest

@Composable
fun FriendsScreen(
    friendsVM: FriendsVM = hiltViewModel(),
    goToFightWithFriend: (String)->Unit,
) {
    val state by remember { friendsVM.state }
    val sideEffect by friendsVM.sideEffect
    LaunchedEffect(sideEffect){
        when(val objectSideEffect = sideEffect){
            is FriendsSideEffect.GoToFightWithFriend -> {
                friendsVM.onEvent(FriendsEvent.ClearSide)
                goToFightWithFriend(objectSideEffect.email)
            }
            null -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp,)
    ){
        SearchUser(friendsVM::onEvent)
        Spacer(modifier = Modifier.height(24.dp))
        when(state){
            FriendsState.BadInternetConnection -> RetryRequest {
                friendsVM.onEvent(FriendsEvent.RetryRequest)
            }
            FriendsState.Loading -> LoadingIndicator()
            is FriendsState.FriendList -> FriendList(
                friends = (state as FriendsState.FriendList).friends,
                friendsVM::onEvent
            )
            is FriendsState.FoundUserList ->UserList(
                (state as FriendsState.FoundUserList).userList,
                friendsVM::onEvent
            )
        }
    }
}

@Composable
private fun SearchUser(
    search: (FriendsEvent.SearchUser)->Unit,
) {
    var nameForSearch by remember {
        mutableStateOf("")
    }
    val style = TextStyle(
        color = White95,
        fontFamily = montserratFont,
        fontSize = 16.sp
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DenseBlue)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(26.dp)
        )
        BasicTextField(
            value = nameForSearch ,
            onValueChange =
            {
                nameForSearch = it
                search(FriendsEvent.SearchUser(nameForSearch))
            },
            textStyle = style,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            cursorBrush = SolidColor(White95),
            decorationBox = { innerTextField->
                if (nameForSearch.isEmpty()){
                    Text(
                        text = "Введите имя и фамилию",
                        style = style.copy(White95.copy(0.5f))
                    )
                }
                innerTextField()
            }
        )
        AnimatedVisibility(
            visible = nameForSearch.isNotBlank(),
            modifier = Modifier
                .padding(start = 12.dp)
                .size(26.dp)
                .clip(CircleShape),
            enter = fadeIn() + expandHorizontally(expandFrom = Alignment.Start),
            exit = fadeOut() + shrinkHorizontally(shrinkTowards = Alignment.Start),
        ) {
            IconButton(
                onClick = {
                    nameForSearch = ""
                    search(FriendsEvent.SearchUser(""))
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                )
            }
        }
    }
}