package ru.okei.med.feature.rating.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.domain.model.RatingInfo
import ru.okei.med.feature.theme.Blue
import ru.okei.med.feature.theme.Purple
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun ItemPlayerRatingInfo(
    player: RatingInfo.Player,
    emailOwnerAccount:String,
    shape: Shape = RoundedCornerShape(10.dp),
    sizeIcon: DpSize = DpSize(48.dp,48.dp),
    borderStroke: BorderStroke = BorderStroke(1.dp, White95.copy(0.5f)),
    paddingContent: PaddingValues = PaddingValues(12.dp),
    sizeMainText: TextUnit = 13.5.sp,
    sizeSideText: TextUnit = 12.sp,
) {
    val color = if(player.email == emailOwnerAccount) Purple else Blue
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(shape)
            .background(color, shape)
            .border(borderStroke, shape)
            .padding(paddingContent),
        verticalAlignment = Alignment.CenterVertically
    ){
        GlideImage(
            imageModel = player.icon,
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
                text = player.name,
                fontFamily = montserratFont,
                fontWeight = FontWeight.W600,
                fontSize = sizeMainText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = player.email,
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
                text = "#${player.placeInRatingDepartment} место",
                fontFamily = montserratFont,
                fontWeight = FontWeight.W600,
                fontSize = sizeMainText,
                textAlign = TextAlign.End,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = "${player.numberPointsInRatingDepartment} ${formatPointsText(player.numberPointsInRatingDepartment)}",
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

private fun formatPointsText(number:Int):String{
    if(number in 10..19) return "очков"
    return when(number%10){
        1 -> "очко"
        in 2..4 -> "очка"
        else -> "очков"
    }
}