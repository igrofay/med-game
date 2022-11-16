package ru.okei.med.feature.main_screen.view

import androidx.compose.runtime.Composable
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
    sizeBoxProfile: DpSize,
    sizeBoxItems: DpSize,
    editProfile:()->Unit
) {
    val sizeImg = DpSize(50.dp,50.dp)
    ItemProfileBox(
        image = profile.urlIcon,
        name = profile.nickname,
        email = profile.mail,
        sizeBox = sizeBoxProfile,
        background = Purple,
        clickable = editProfile
    )
    ItemActionBox(
        image = R.drawable.newsletter_talk,
        label = stringResource(R.string.friends),
        sizeBox = sizeBoxItems.copy(height = sizeBoxItems.height/1.75f),
        background = DenseBlue,
        fontSize = defSmallTextUnit,
        sizeImage = sizeImg,
        visibleCircularBackground = false
    ) {

    }
    ItemActionBox(
        image = R.drawable.idea,
        label = stringResource(R.string.rating),
        sizeBox = sizeBoxItems.copy(height = sizeBoxItems.height/1.75f),
        background = DenseBlue,
        fontSize = defSmallTextUnit,
        sizeImage = sizeImg,
        visibleCircularBackground = false
    ) {

    }
}
