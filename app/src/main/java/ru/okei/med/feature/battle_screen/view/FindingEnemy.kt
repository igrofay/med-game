package ru.okei.med.feature.battle_screen.view

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.CustomButton

@Composable
fun FindingEnemy(
    exit:()->Unit
) {
    when(LocalConfiguration.current.orientation){
        Configuration.ORIENTATION_PORTRAIT -> VerticalFindingEnemy(exit)
        else -> HorizontalFindingEnemy(exit)
    }
}

@Composable
private fun animateBlink() : Float {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000 ),
            repeatMode = RepeatMode.Restart,
        ),
    )
    return alpha
}

@Composable
private fun VerticalFindingEnemy(
    exit:()->Unit
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
        ) {
            Image(
                painter = painterResource(R.drawable.thinking),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .background(Color.White.copy(0.1f), CircleShape)
                    .padding(28.dp)
                    .background(Color.Gray.copy(0.1f), CircleShape)
                    .alpha(animateBlink())
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
                    text = stringResource(R.string.looking_for_your_opponent),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = White95
                )
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
            }
            Spacer(modifier = Modifier.height(size/8))
            CustomButton(
                label = stringResource(R.string.cancel),
                textSize = 14.sp,
                size = DpSize(250.dp,52.dp),
                onClick = exit
            )
        }
    }
}

@Composable
private fun HorizontalFindingEnemy(
    exit:()->Unit
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
                painter = painterResource(R.drawable.thinking),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .background(Color.White.copy(0.1f), CircleShape)
                    .padding(28.dp)
                    .background(Color.Gray.copy(0.1f), CircleShape)
                    .alpha(animateBlink())
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
                    text = stringResource(R.string.looking_for_your_opponent),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = White95
                )
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray.copy(0.4f)))
            }
            Spacer(modifier = Modifier.weight(0.2f))
            CustomButton(
                label = stringResource(R.string.cancel),
                textSize = 14.sp,
                size = DpSize(250.dp,52.dp),
                onClick = exit
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

