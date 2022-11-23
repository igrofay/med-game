package ru.okei.med.feature.main_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.R
import ru.okei.med.domain.model.TypeBattle
import ru.okei.med.feature.main_screen.view_model.MainVM
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.RowWithWrap

@Composable
fun MainScreen(
    layerBattles:(String)->Unit,
    battleRating:(String)->Unit,
    editProfile:()->Unit,
    openFriend: ()->Unit,
    mainVM : MainVM = hiltViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val state by remember { mainVM.state }
    val errorMessage by mainVM.error
    val sizeBoxItems = DpSize(155.dp,155.dp)
    val sizeBoxItemProfile = DpSize(318.dp,155.dp)
    LaunchedEffect(errorMessage){
        errorMessage?.message?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Header()
            RowWithWrap(
                modifier = Modifier.fillMaxWidth(),
                verticalSpacer = 8.dp, horizontalSpacer = 8.dp
            ){
                Games(
                    sizeBox = sizeBoxItems,
                    layerBattles = { layerBattles(TypeBattle.Simple.name) },
                    battleRating = { battleRating(TypeBattle.Rating.name)}
                )
                Profile(
                    profile = state.profileBody,
                    sizeBoxProfile = sizeBoxItemProfile,
                    sizeBoxItems = sizeBoxItems,
                    editProfile = editProfile,
                    openFriend = openFriend
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 24.dp)
    ){
        Spacer(modifier = Modifier
            .height(1.dp)
            .weight(1f)
            .background(Color.Gray.copy(0.4f)))
        Text(
            text = stringResource(R.string.start_game),
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
