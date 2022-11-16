package ru.okei.med.feature.battle_screen.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.domain.model.StateGame
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.CustomButton
import ru.okei.med.feature.widget.RowWithWrap

@Composable
fun RatingGame(
    statusGame: StateGame,
    goToMain:()->Unit
) {
    when(LocalConfiguration.current.orientation){
        Configuration.ORIENTATION_PORTRAIT -> VerticalRatingGame(statusGame,goToMain)
        else -> HorizontalRatingGame(statusGame,goToMain)
    }
}

@Composable
private fun VerticalRatingGame(
    statusGame: StateGame,
    goToMain:()->Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AppTopBar()
        Spacer(modifier = Modifier.weight(0.3f))
        if(statusGame.isEndGame){
            Text(
                text = "Игра окончена",
                fontWeight = FontWeight.W600,
                fontSize = 30.sp,
                color = White95,
                fontFamily = montserratFont,
            )
            statusGame.nameWinner?.let { nameWinner->
                Text(
                    text = "Победил: $nameWinner",
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp,
                    color = White95,
                    fontFamily = montserratFont,
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.7f))
        RowWithWrap(
            verticalSpacer = 30.dp,
            horizontalSpacer = 30.dp
        ){
            for(item in statusGame.rating){
                RatingPersonItem(
                    rating = item,
                    size = DpSize(100.dp,100.dp),
                    fontSizeNameText = 18.sp,
                    fontSizeNamePointsText = 12.sp,
                    fontSizePointsText = 30.sp)
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
        if(statusGame.isEndGame){
            CustomButton(
                label = "Главная",
                textSize = 14.sp,
                size = DpSize(250.dp,52.dp),
                onClick = goToMain
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}


@Composable
private fun HorizontalRatingGame(
    statusGame: StateGame,
    goToMain:()->Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        AppTopBar()
        Row {
            RowWithWrap(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalSpacer = 30.dp,
                horizontalSpacer = 30.dp
            ){
                for(item in statusGame.rating){
                    RatingPersonItem(
                        rating = item,
                        size = DpSize(100.dp,100.dp),
                        fontSizeNameText = 18.sp,
                        fontSizeNamePointsText = 12.sp,
                        fontSizePointsText = 30.sp)
                }
            }
            if(statusGame.isEndGame){
                Column(
                    Modifier.weight(0.75f).fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Игра окончена",
                        fontWeight = FontWeight.W600,
                        fontSize = 30.sp,
                        color = White95,
                        fontFamily = montserratFont,
                    )
                    statusGame.nameWinner?.let { nameWinner->
                        Text(
                            text = "Победил: $nameWinner",
                            fontWeight = FontWeight.W400,
                            fontSize = 18.sp,
                            color = White95,
                            fontFamily = montserratFont,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    CustomButton(
                        label = "Главная",
                        textSize = 14.sp,
                        size = DpSize(250.dp,52.dp),
                        onClick = goToMain
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }
    }
}