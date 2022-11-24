package ru.okei.med.feature.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun RetryRequest(
    retry: ()->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Произошла ошибка в подключение",
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = 17.sp,
            color = White95,
            modifier = Modifier.fillMaxWidth(0.5f),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            label = "Повторить",
            textSize = 14.sp,
            size = DpSize(120.dp,40.dp),
            onClick = retry
        )
    }
}