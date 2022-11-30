package ru.okei.med.feature.rating.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.domain.model.Department
import ru.okei.med.domain.model.RatingInfo
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.AppTopBar

@Composable
fun RatingList(
    ratingInfo: RatingInfo,
    emailOwnerAccount:String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .fillMaxHeight(),

        ){
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                HeaderDepartment(ratingInfo.department)
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(ratingInfo.listPlayers){ player: RatingInfo.Player ->
                ItemPlayerRatingInfo(player,emailOwnerAccount)
            }
        }
    }
}

@Composable
fun HeaderDepartment(
    department: Department
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
        Text(
            text = "Рейтинг\n${department.departmentName}",
            fontFamily = montserratFont,
            fontWeight = FontWeight.W600,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
    }
}