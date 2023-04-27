package ru.okei.med.feature.friends.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.theme.DenseBlue
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FriendList(
    friends: Map<FriendInfo.FriendStatus, List<FriendInfo>>,
    action: (FriendsEvent)->Unit
) {
    val keys = remember{ friends.keys.toList() }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()){
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = DenseBlue,
            contentColor = White95,
            edgePadding = 12.dp,
            divider = {},
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            keys.forEachIndexed { index, status ->
                Tab(
                    text = {
                        Text(
                            status.statusName,
                            fontFamily = montserratFont,
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                              scope.launch {
                                  pagerState.scrollToPage(index)
                              }
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(
            count = friends.size,
            state = pagerState,
        ) { page->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(
                    items = friends[keys[page]]!!
                ){ friendInfo ->
                    ItemFriendInfo(friendInfo,action = action)
                }
            }
        }
    }
}

