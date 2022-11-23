package ru.okei.med.feature.profile_screen.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.feature.profile_screen.model.ProfileEvent
import ru.okei.med.feature.theme.Purple
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun ProfileInfo(
    profileBody: ProfileBody,
    onChange: (ProfileEvent)->Unit
) {
    var imageUrl by remember {
        mutableStateOf(profileBody.urlIcon)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult ={ uri: Uri? -> uri?.let {  uriIsNotNull->
            imageUrl = uriIsNotNull.toString()
            onChange(ProfileEvent.ChangeImage(uriIsNotNull))
        } }
    )
    val shape = RoundedCornerShape(10)
    val color = Purple
    val border = BorderStroke(2.dp, White95.copy(0.2f))
    Column(
        modifier = Modifier
            .size(300.dp)
            .clip(shape)
            .background(color, shape)
            .border(border, shape),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(160.dp + 14.dp)){
            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .border(border, CircleShape)
                    .padding(8.dp)
                    .background(border.brush, CircleShape)
                    .padding(6.dp)
                    .clip(CircleShape),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                failure = { ImageProfileDefault() },
                loading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                },
            )
            FloatingActionButton(
                onClick = { launcher.launch("image/*") },
                backgroundColor = White95,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-7).dp)
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = color
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = profileBody.nickname,
            fontWeight = FontWeight.W600,
            fontSize = 22.sp,
            maxLines = 3,
            color = White95,
            fontFamily = montserratFont,
            modifier = Modifier.widthIn(max = 250.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = profileBody.mail,
            fontFamily = montserratFont,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            maxLines = 1,
            color = White95,
            modifier = Modifier.widthIn(max = 250.dp),
            textAlign = TextAlign.Center,
        )
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