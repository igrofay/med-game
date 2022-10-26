package ru.okei.med.feature.battle_screen.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.battle_screen.model.BattleEvent
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.battle_screen.view_model.BattleVM

@Composable
fun BattleScreen(
    battleVM: BattleVM = hiltViewModel()
) {
    //BackHandler {}
    val state by remember { battleVM.state }
    when(state){
        BattleState.FindingEnemy ->
            FindingEnemy(
                exit = {}
            )
        is BattleState.Loading -> Loading(
            loadState = (state as BattleState.Loading).load
        ) {

        }
        is BattleState.ModuleSelection -> ModuleSelection(
            modules = (state as BattleState.ModuleSelection).modules,
            onSelection = { }
        )
        is BattleState.QuestionForm -> Question(
            questionForm = state as BattleState.QuestionForm,
            onAnswer = { answer ->
                battleVM.onEvent(BattleEvent.Reply(answer))
            }
        )
    }
}