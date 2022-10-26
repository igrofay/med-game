package ru.okei.med.feature.auth_screen.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import ru.okei.med.R
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.widget.AppTopBar

@Composable
fun AuthLogo(
    isVertical:Boolean
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f ,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val rotate by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 15f ,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(
                if (isVertical) 1f else 0.5f
            )
            .fillMaxHeight(
                if (isVertical) 0.5f else 1f
            ),
        contentAlignment = Alignment.TopCenter
    ){
        val size = min(maxHeight,maxWidth) /1.4f
        Image(
            painter = painterResource(R.drawable.trophy),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .background(Color.White.copy(0.1f),CircleShape)
                .padding(28.dp)
                .background(Color.Gray.copy(0.1f),CircleShape)
                .scale(scale).rotate(rotate)
        )
    }
}
