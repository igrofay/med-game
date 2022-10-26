package ru.okei.med.feature.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun AppTopBar() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
       Icon(
           painter = painterResource(R.drawable.ic_logo) , 
           contentDescription =null,
           modifier = Modifier.size(30.dp),
           tint = White95,
       )
        Text(
            text = stringResource(R.string.app_name),
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            color = White95,
        )
    }
}