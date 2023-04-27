package ru.okei.med.feature.main_screen.view

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.domain.model.RequestForFight
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.friends.view.ItemMainInfo
import ru.okei.med.feature.main_screen.model.MainEvent
import ru.okei.med.feature.theme.Green
import ru.okei.med.feature.theme.Purple
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun BoxRequestFight(
    requestForFight: RequestForFight?,
    eventBase: EventBase<MainEvent>,
) {
    AnimatedVisibility(
        visible = requestForFight != null,
        enter =  fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        val staticState = remember{ requestForFight!!.friendInfo }
        val shape = remember {
            RoundedCornerShape(10.dp)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp)
                .clip(shape)
                .background(Color(0xFF050723))
                .border(1.dp, White95.copy(0.5f), shape),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(12.dp)
                    .clip(shape)
                    .background(Purple, shape)
                    .border(BorderStroke(1.dp, White95.copy(0.5f)), shape)
                    .padding(12.dp)
            ){
                ItemMainInfo(
                    staticState,
                    DpSize(48.dp,48.dp),
                    BorderStroke(1.dp, White95.copy(0.5f)),
                    13.5.sp,
                    12.sp,
                    RoundedCornerShape(10.dp)
                )
            }
            val module = remember {
                requestForFight!!.module
            }
            Text(
                text = "Вас приглашают на бой\n$module",
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                fontFamily = montserratFont,
                fontWeight = FontWeight.W600,
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TextButton(onClick = { eventBase.onEvent(MainEvent.RefuseToFight) }) {
                    Text(
                        text = "Отказаться",
                        color = MaterialTheme.colors.error,
                        fontSize = 13.5.sp,
                        fontFamily = montserratFont,
                    )
                }
                TextButton(onClick = { eventBase.onEvent(MainEvent.AcceptFight) }) {
                    Text(
                        text = "Принять",
                        color = Green,
                        fontSize = 13.5.sp,
                        fontFamily = montserratFont,
                    )
                }
            }
        }
    }
}