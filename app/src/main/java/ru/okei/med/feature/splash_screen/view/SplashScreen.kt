package ru.okei.med.feature.splash_screen.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ru.okei.med.R
import ru.okei.med.feature.splash_screen.model.SplashState
import ru.okei.med.feature.splash_screen.view_model.SplashVM
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.LoadingIndicator

@Composable
fun SplashScreen(
    splashViewModel: SplashVM = hiltViewModel(),
    sessionCreated:()->Unit,
    sessionNotCreated:()->Unit,
) {
    val keyState by splashViewModel.state
    LaunchedEffect(keyState){
        delay(1_000)
        when(keyState){
            SplashState.Error -> sessionNotCreated()
            SplashState.Loading -> {}
            SplashState.Success -> sessionCreated()
        }
    }
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f ,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val size = min(maxHeight,maxWidth)/1.5f
        AppTopBar()
        Image(
            painter = painterResource(R.drawable.moon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 30.dp)
                .size(size)
                .background(Color.White.copy(0.1f), CircleShape)
                .padding(28.dp)
                .background(Color.Gray.copy(0.1f), CircleShape)
                .scale(scale)
        )
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 32.dp+size),
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            fontFamily = montserratFont,
            color = White95,
        )
    }
}