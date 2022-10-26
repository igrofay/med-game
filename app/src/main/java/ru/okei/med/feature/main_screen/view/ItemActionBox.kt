package ru.okei.med.feature.main_screen.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

val defTextUnit = 14.sp
val defBorder = BorderStroke(2.dp, Color.White.copy(0.2f))
val defShape = RoundedCornerShape(20)

@Composable
fun<T> ItemActionBox(
    image: T,
    label: String,
    sizeBox: DpSize,
    sizeImage: DpSize = DpSize(80.dp,80.dp),
    background: Color,
    fontSize: TextUnit = defTextUnit,
    border: BorderStroke = defBorder,
    shape: Shape = defShape,
    visibleCircularBackground:Boolean = true,
    clickable: ()->Unit
) {
    Column(
        modifier = Modifier
            .height(sizeBox.height)
            .width(sizeBox.width)
            .clip(shape)
            .background(background, shape)
            .border(border, shape)
            .clickable(onClick = clickable),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .height(sizeImage.height)
                .width(sizeImage.width)
        ){
            if(visibleCircularBackground)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(border, CircleShape)
                        .padding(8.dp)
                        .background(border.brush, CircleShape)
                        .padding(6.dp)
                )
            GlideImage(
                imageModel = image,
                modifier = Modifier.fillMaxSize(),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
        }
        Text(
            text = label,
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = fontSize,
            modifier = Modifier.width(sizeImage.width+14.dp),
            textAlign = TextAlign.Center,
            color = White95,
        )
    }
}

@Composable
fun ItemProfileBox(
    image: String,
    label: String,
    sizeBox: DpSize,
    background: Color,
    sizeImage: DpSize = DpSize(80.dp,80.dp),
    fontSize: TextUnit = defTextUnit,
    border: BorderStroke = defBorder,
    shape: Shape = defShape,
    clickable: ()->Unit
) {
    Column(
        modifier = Modifier
            .height(sizeBox.height)
            .width(sizeBox.width)
            .clip(shape)
            .background(background, shape)
            .border(border, shape)
            .clickable(onClick = clickable),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        GlideImage(
            imageModel = image,
            modifier = Modifier
                .height(sizeImage.height + 14.dp)
                .width(sizeImage.width + 14.dp)
                .border(border, CircleShape)
                .padding(8.dp)
                .background(border.brush, CircleShape)
                .padding(6.dp)
                .clip(CircleShape),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            failure = { ImageDefault() },
            loading = { ImageDefault() },
        )
        Text(
            text = label,
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = fontSize,
            modifier = Modifier.width(sizeImage.width+14.dp),
            textAlign = TextAlign.Center,
            maxLines = 2,
            color = White95,
        )
    }
}

@Composable
private fun ImageDefault() {
    Image(
        painter = painterResource(R.drawable.ic_logo_profile),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}