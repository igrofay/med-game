package ru.okei.med.feature.battle_screen.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import ru.okei.med.R
import ru.okei.med.domain.model.StateGame
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RatingPersonItem(
    rating: StateGame.GamePointsRating,
    size: DpSize,
    fontSizeNameText: TextUnit,
    fontSizeNamePointsText: TextUnit,
    fontSizePointsText: TextUnit,
    strokeWidth: Dp = 5.dp,
) {
    var visibleImagePerson by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit){
        delay(600)
        visibleImagePerson = false
    }
    val percentage by animateFloatAsState(
        targetValue = if(visibleImagePerson) rating.passLastPercentage else rating.passPercentage,
        animationSpec = tween(
            durationMillis = 900,
            delayMillis = 250
        )
    )
    val countOfPoints by animateIntAsState(
        targetValue = if(visibleImagePerson) rating.lastCountOfPoints else rating.countOfPoints,
        animationSpec = tween(
            durationMillis = 900,
            delayMillis = 250
        )
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text =rating.nickname,
            fontWeight = FontWeight.W400,
            fontSize = fontSizeNameText,
            color = White95,
            modifier = Modifier.width(size.width),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontFamily = montserratFont,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ){
            Canvas(modifier = Modifier.fillMaxSize()){
                drawArc(
                    color = White95.copy(0.5f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx() , cap = StrokeCap.Round)
                )
                drawArc(
                    color = White95,
                    startAngle = -90f,
                    sweepAngle = 3.6f*percentage,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx() , cap = StrokeCap.Round)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .widthIn(max = size.width - strokeWidth * 2)
                    .heightIn(max = size.width - strokeWidth * 2)
            ){
                Text(
                    text = AnnotatedString(
                        text = "$countOfPoints\n",
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = fontSizePointsText,
                            fontFamily = montserratFont,
                            color = White95,
                        )
                    ).plus(
                        AnnotatedString(
                            text = formatPointsText(countOfPoints),
                            spanStyle = SpanStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = fontSizeNamePointsText,
                                fontFamily = montserratFont,
                                color = White95,
                            )
                        )
                    ),
                    lineHeight = fontSizeNamePointsText*1.25,
                    textAlign = TextAlign.Center
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = visibleImagePerson,
                enter = scaleIn(),
                exit = scaleOut(tween(durationMillis = 250)),
            ) {
                GlideImage(
                    imageModel = rating.image,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    failure = { ImageProfileDefault()},
                    loading = { ImageProfileDefault() }
                )
            }
        }
    }
}

fun formatPointsText(number:Int):String{
    if(number in 10..19) return "Баллов"
    return when(number%10){
        1 -> "Балл"
        in 2..4 -> "Балла"
        else -> "Баллов"
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