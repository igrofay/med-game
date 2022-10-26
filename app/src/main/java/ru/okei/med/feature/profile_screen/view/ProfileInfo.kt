package ru.okei.med.feature.profile_screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody

@Composable
fun ProfileInfo(
    profileBody: ProfileBody
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            imageModel = profileBody.urlIcon,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            failure = {
                ImageProfileDefault()
            },
            loading = { ImageProfileDefault() }
        )
        Spacer(modifier = Modifier.width(18.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = profileBody.nickname,
                fontSize = 25.sp,
                fontWeight = FontWeight.W700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = profileBody.mail,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
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