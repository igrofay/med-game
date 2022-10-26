package ru.okei.med.feature.teaching_screen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.noRippleClickable

@Composable
fun StartTraining(
    skip: ()->Unit
) {
    Column {
        Box{
            AppTopBar()
            Row(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 14.dp)
                    .noRippleClickable { }
            ){
                Text(
                    text = stringResource(R.string.skip),
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
                )
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}