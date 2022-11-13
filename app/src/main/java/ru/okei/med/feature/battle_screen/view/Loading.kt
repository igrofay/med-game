package ru.okei.med.feature.battle_screen.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.CustomButton

@Composable
fun Loading(
    loadState: BattleState.Loading.Load,
) {
    when(LocalConfiguration.current.orientation){
        Configuration.ORIENTATION_PORTRAIT -> VerticalLoading(loadState)
        else -> HorizontalLoading(loadState,)
    }
}

@Composable
private fun VerticalLoading(
    loadState: BattleState.Loading.Load,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        val size = min(maxHeight, maxWidth) /1.5f
        AppTopBar()
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.repair_tool),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .background(Color.White.copy(0.1f), CircleShape)
                    .padding(28.dp)
                    .background(Color.Gray.copy(0.1f), CircleShape)
            )
            Spacer(modifier = Modifier.height(size/14))
            Row(
                modifier = Modifier.width(size),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
                Text(
                    text =
                    if(loadState == BattleState.Loading.Load.EnemyConnection)
                        stringResource(R.string.opposites_found)
                    else stringResource(R.string.loading),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    color = White95,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
            }
            if(loadState == BattleState.Loading.Load.EnemyConnection)
                Text(
                    text = stringResource(R.string.connecting),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = White95,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            Spacer(modifier = Modifier.height(size/8))
//            CustomButton(
//                label = stringResource(R.string.cancel),
//                textSize = 14.sp,
//                size = DpSize(250.dp,52.dp),
//                onClick = cancellation
//            )
        }
    }
}


@Composable
fun HorizontalLoading(
    loadState: BattleState.Loading.Load,
) {
    Row {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val size = min(maxHeight, maxWidth) /1.5f
            Image(
                painter =painterResource(R.drawable.repair_tool),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .background(Color.White.copy(0.1f), CircleShape)
                    .padding(28.dp)
                    .background(Color.Gray.copy(0.1f), CircleShape)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar()
            Spacer(modifier = Modifier.weight(0.5f))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
                Text(
                    text =
                    if(loadState == BattleState.Loading.Load.EnemyConnection)
                        stringResource(R.string.opposites_found)
                    else stringResource(R.string.loading),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    color = White95,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
            }
            if(loadState == BattleState.Loading.Load.EnemyConnection)
                Text(
                    text = stringResource(R.string.connecting),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = White95,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}