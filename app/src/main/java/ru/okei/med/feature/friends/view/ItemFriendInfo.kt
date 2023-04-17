package ru.okei.med.feature.friends.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.feature.friends.model.FriendsEvent
import ru.okei.med.feature.theme.*

@Composable
fun ItemFriendInfo(
    friendInfo: FriendInfo,
    shape: Shape = RoundedCornerShape(10.dp),
    sizeIcon: DpSize = DpSize(48.dp,48.dp),
    borderStroke: BorderStroke = BorderStroke(1.dp, White95.copy(0.5f)),
    paddingContent: PaddingValues = PaddingValues(12.dp),
    sizeMainText: TextUnit = 13.5.sp,
    sizeSideText: TextUnit = 12.sp,
    action: (FriendsEvent)->Unit,
) {
    var isItemInfoProfile by remember { mutableStateOf(true) }
    val color = when(friendInfo.status){
        FriendInfo.FriendStatus.Friend -> Purple
        FriendInfo.FriendStatus.ApplicationSent -> Green
        FriendInfo.FriendStatus.Subscriber -> Orange
        FriendInfo.FriendStatus.NotFriends -> Blue
    }
    val rotateX by animateFloatAsState(
        targetValue = if(isItemInfoProfile) 0f else 180f,
        animationSpec = tween(
            durationMillis = 200
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(.98f)
            .height(IntrinsicSize.Min)
            .graphicsLayer { rotationX = rotateX }
            .clip(shape)
            .clickable { isItemInfoProfile = !isItemInfoProfile }
            .background(color, shape)
            .border(borderStroke, shape)
            .padding(paddingContent)
    ){
        ItemMainInfo(
            friendInfo,
            sizeIcon,
            borderStroke,
            sizeMainText,
            sizeSideText
        )
        if(rotateX>=90f){
            SettingsItem(
                friendInfo.status,
                friendInfo.email,
                color,
                sizeIcon,
                settingsSiteText = 16.sp,
                action = action
            )
        }
    }
}

@Composable
fun ItemMainInfo(
    friendInfo: FriendInfo,
    sizeIcon: DpSize,
    borderStroke: BorderStroke,
    sizeMainText: TextUnit,
    sizeSideText: TextUnit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        GlideImage(
            imageModel = friendInfo.icon,
            modifier = Modifier
                .border(borderStroke, CircleShape)
                .padding(borderStroke.width)
                .size(sizeIcon)
                .clip(CircleShape),
            loading = { LoadingImageDefault() },
            failure = { ImageProfileDefault() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(2f)
        ){
            Text(
                text = friendInfo.name,
                fontFamily = montserratFont,
                fontWeight = FontWeight.W600,
                fontSize = sizeMainText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = friendInfo.email,
                fontFamily = montserratFont,
                fontWeight = FontWeight.W400,
                maxLines = 1,
                fontSize = sizeSideText,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.weight(0.4f))
        Column(
            modifier = Modifier.weight(1.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = friendInfo.department.departmentName,
                fontFamily = montserratFont,
                fontWeight = FontWeight.W600,
                fontSize = sizeMainText,
                textAlign = TextAlign.End,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${friendInfo.numberPointsInRatingDepartment} ${formatPointsText(friendInfo.numberPointsInRatingDepartment)}",
                fontFamily = montserratFont,
                fontWeight = FontWeight.W400,
                fontSize = sizeSideText,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun SettingsItem(
    friendStatus: FriendInfo.FriendStatus,
    email:String,
    backgroundColor:Color,
    iconSize: DpSize,
    settingsSiteText:TextUnit,
    action: (FriendsEvent)->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { rotationX = 180f }
            .background(backgroundColor)
    ) {
        if (friendStatus == FriendInfo.FriendStatus.Friend){
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Бой",
                    fontSize = settingsSiteText,
                    fontWeight = FontWeight.W600,
                    color = White95,
                    fontFamily = montserratFont
                )
                IconButton(
                    onClick = { action.invoke(FriendsEvent.FightFriendRequest(email)) },
                    modifier = Modifier
                        .size(iconSize)
                        .padding(8.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_games_selected),
                        contentDescription = null,
                        tint = White95,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(0.9f)
                .background(Color.White.copy(0.6f))
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            when(friendStatus){
                FriendInfo.FriendStatus.Friend -> {
                    Text(
                        text = "Удалить из друзей",
                        fontSize = settingsSiteText,
                        fontWeight = FontWeight.W600,
                        color = White95,
                        fontFamily = montserratFont
                    )
                }
                FriendInfo.FriendStatus.ApplicationSent -> {
                    Text(
                        text = "Отменить заявку",
                        fontSize = settingsSiteText,
                        fontWeight = FontWeight.W600,
                        color = White95,
                        fontFamily = montserratFont
                    )
                }
                FriendInfo.FriendStatus.Subscriber -> {
                    Text(
                        text = "Принять заявку",
                        fontSize = settingsSiteText,
                        fontWeight = FontWeight.W600,
                        color = White95,
                        fontFamily = montserratFont
                    )
                }
                FriendInfo.FriendStatus.NotFriends -> {
                    Text(
                        text = "Отправить заявку",
                        fontSize = settingsSiteText,
                        fontWeight = FontWeight.W600,
                        color = White95,
                        fontFamily = montserratFont
                    )
                }
            }
            when(friendStatus){
                FriendInfo.FriendStatus.Friend -> {
                    IconButton(
                        onClick = { action.invoke(FriendsEvent.DeleteFriend(email)) },
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = White95,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                FriendInfo.FriendStatus.ApplicationSent ->{
                    IconButton(
                        onClick = { action.invoke(FriendsEvent.CanselFriendRequest(email)) },
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White95,
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(45f)
                        )
                    }
                }
                FriendInfo.FriendStatus.Subscriber ->{
                    IconButton(
                        onClick = { action.invoke(FriendsEvent.AcceptFriendRequest(email)) },
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White95,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                FriendInfo.FriendStatus.NotFriends -> {
                    IconButton(
                        onClick = { action.invoke(FriendsEvent.SendFriendRequest(email)) },
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White95,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }

}



private fun formatPointsText(number:Int):String{
    if(number in 10..19) return "очков"
    return when(number%10){
        1 -> "очко"
        in 2..4 -> "очка"
        else -> "очков"
    }
}

@Composable
private fun LoadingImageDefault() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}

@Composable
private fun ImageProfileDefault() {
    Image(
        painter = painterResource(R.drawable.ic_logo_profile),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}