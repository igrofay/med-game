package ru.okei.med.feature.battle_screen.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.feature.theme.DenseBlue
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont

@Composable
fun ModuleSelection(
    modules: List<String>,
    onSelection: (String)->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        for (module in modules){
            ItemModule(
                string = module
            ) {
                onSelection(module)
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 24.dp)
    ){
        Box(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
        Text(
            text = stringResource(R.string.select_module),
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            color = White95,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Box(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
    }
}

@Composable
private fun ItemModule(
    string: String,
    onClick: ()->Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(DenseBlue, CircleShape)
            .border(BorderStroke(2.dp, Color.White.copy(0.2f)), CircleShape)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = string,
            fontFamily = montserratFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            color = White95,
        )
    }
}