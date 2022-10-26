package ru.okei.med.feature.main_screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.feature.theme.DenseBlue
import ru.okei.med.feature.theme.Purple

@Composable
fun Profile(
    profile: ProfileBody,
    sizeBox: DpSize
) {
    val sizeImg = DpSize(40.dp,40.dp)
    ItemProfileBox(
        image = profile.urlIcon,
        label = profile.nickname,
        sizeBox = sizeBox,
        background = Purple
    ) {

    }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ItemActionBox(
            image = R.drawable.newsletter_talk,
            label = stringResource(R.string.friends),
            sizeBox = sizeBox.copy(height = sizeBox.height/2.1f),
            background = DenseBlue,
            fontSize = 10.sp,
            sizeImage = sizeImg,
            visibleCircularBackground = false
        ) {

        }
        Spacer(modifier = Modifier.height(sizeBox.height / 21))
        ItemActionBox(
            image = R.drawable.idea,
            label = stringResource(R.string.rating),
            sizeBox = sizeBox.copy(height = sizeBox.height/2.1f),
            background = DenseBlue,
            fontSize = 10.sp,
            sizeImage = sizeImg,
            visibleCircularBackground = false
        ) {

        }
    }
}
