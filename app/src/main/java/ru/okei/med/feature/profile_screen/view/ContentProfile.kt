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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.feature.theme.montserratFont

@Composable
fun ContentProfile(
    profileBody: ProfileBody
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ){
        Header(label = "Профиль")

        Header(label = "Кафедра")
    }
}

@Composable
private fun Header(label:String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 24.dp)
    ){
        Spacer(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
        Text(
            text = label,
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
    }
}