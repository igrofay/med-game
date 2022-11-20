package ru.okei.med.feature.profile_screen.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.domain.model.Department
import ru.okei.med.feature.theme.Purple
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun DepartmentInfo(label: Department) {
    val shape = RoundedCornerShape(20.dp)
    val color = Purple
    val border = BorderStroke(2.dp, White95.copy(0.2f))
    Box(modifier = Modifier
        .width(300.dp)
        .clip(shape)
        .background(color)
        .border(border, shape),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = label.departmentName,
            fontFamily = montserratFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.widthIn(max = 280.dp).padding(20.dp)
        )
    }
}