package ru.okei.med.feature.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import ru.okei.med.feature.theme.White95

@Composable
fun CustomButton(
    label: String,
    textSize: TextUnit,
    size: DpSize,
    shape: RoundedCornerShape = RoundedCornerShape(100),
    onClick: ()-> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF54A3FF),Color(0xFF0075FF))
                ), shape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = label,
            fontSize = textSize,
            fontWeight = FontWeight.W400,
            maxLines = 1,
            color = White95,
        )
    }

}