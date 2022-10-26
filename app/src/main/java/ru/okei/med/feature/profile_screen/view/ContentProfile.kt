package ru.okei.med.feature.profile_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody

@Composable
fun ContentProfile(
    profileBody: ProfileBody
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground)
            )
            Text(
                text = stringResource(R.string.profile),
                fontSize = 24.sp, fontWeight = FontWeight.W900,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground)
            )
        }
        ProfileInfo(
            profileBody = profileBody
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground)
            )
            Text(
                text = stringResource(R.string.achievements),
                fontSize = 24.sp, fontWeight = FontWeight.W900,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground)
            )
        }
//        val size = profileBody.achievements
//            .size.let { size -> if (size<3) size else 3 }
//        for (index in 0 until size){
//            ItemAchievement(achievementBody = profileBody.achievements[index])
//        }
    }
}