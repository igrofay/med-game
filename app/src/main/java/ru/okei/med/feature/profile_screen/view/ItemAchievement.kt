package ru.okei.med.feature.profile_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.domain.model.AchievementBody
import ru.okei.med.feature.theme.Orange
import ru.okei.med.feature.theme.White95


@Composable
fun ItemAchievement(
    achievementBody: AchievementBody
) {
    val shape = RoundedCornerShape(10)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .shadow(8.dp, shape)
            .background(colors.background)
            .border(1.dp, White95.copy(0.2f), shape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            imageModel = achievementBody.urlIcon,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .height(90.dp)
                .width(70.dp)
                .clip(shape)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Column {
            Text(
                text = achievementBody.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
            )
            Text(
                text = achievementBody.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically){
                LinearProgressIndicator(
                    progress = achievementBody.progress,
                    modifier = Modifier
                        .height(10.dp)
                        .weight(1f)
                        .clip(CircleShape),
                    color = Orange,
                    backgroundColor = colors.onBackground
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = achievementBody.textProgress,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                )
            }
        }
   }
}