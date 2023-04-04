package ru.okei.med.feature.battle_screen.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.R
import ru.okei.med.feature.battle_screen.model.BattleEvent
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.battle_screen.view_model.BattleVM
import ru.okei.med.feature.theme.montserratFont

@Composable
fun BattleScreen(
    battleVM: BattleVM = hiltViewModel(),
    goToBack: ()->Unit
) {
    val state by remember { battleVM.state }
    var isDisplayDialogCancelGame by remember {
        mutableStateOf(false)
    }
    BackHandler(
        enabled = state !is BattleState.ModuleSelection &&
                state !is BattleState.Loading
    ){
        if (state !is BattleState.FindingEnemy){
            isDisplayDialogCancelGame = true
        }
    }
    when(state){
        BattleState.FindingEnemy ->
            FindingEnemy(
                exit = {
                    battleVM.onEvent(BattleEvent.Cancel)
                    goToBack()
                }
            )
        is BattleState.Loading -> Loading(
            loadState = (state as BattleState.Loading).load
        )
        is BattleState.ModuleSelection -> ModuleSelection(
            modules = (state as BattleState.ModuleSelection).modules,
            onSelection = {module-> battleVM.onEvent(BattleEvent.ChosenModule(module)) }
        )
        is BattleState.QuestionForm -> Question(
            questionForm = state as BattleState.QuestionForm,
            onAnswer = { answer ->
                battleVM.onEvent(BattleEvent.Reply(answer))
            }
        )
        is BattleState.ViewRatingGame -> RatingGame(
            statusGame = (state as BattleState.ViewRatingGame).statusGame
        ){
            battleVM.onEvent(BattleEvent.Cancel)
            goToBack()
        }
    }
    if (isDisplayDialogCancelGame){
        Dialog(
            onDismissRequest = { isDisplayDialogCancelGame = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .background(Color(0xFF070E24), RoundedCornerShape(10.dp))
                    .padding( top =  20.dp , bottom = 10.dp)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.already_giving_up),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W600,
                    fontSize = 17.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(id = R.string.if_you_leave_you_will_be_awarded_a_defeat),
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.W300,
                    fontSize = 13.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { isDisplayDialogCancelGame = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                   TextButton(onClick = {
                       isDisplayDialogCancelGame = false
                       battleVM.onEvent(BattleEvent.Cancel)
                       goToBack()
                   }) {
                       Text(text = stringResource(id = R.string.surrender))
                   }
                }
            }
        }
    }
}