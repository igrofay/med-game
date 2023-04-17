package ru.okei.med.feature.main_screen.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import ru.okei.med.R
import ru.okei.med.domain.model.TypeBattle
import ru.okei.med.feature.theme.Blue
import ru.okei.med.feature.theme.Green
import ru.okei.med.feature.theme.Purple

@Composable
fun Games(
    sizeBox: DpSize,
    battle:(TypeBattle)->Unit
) {
    ItemActionBox(
        image = R.drawable.trophy,
        label = stringResource(R.string.module_battles),
        sizeBox = sizeBox,
        background = Purple
    ) {
        battle(TypeBattle.Simple)
    }
    ItemActionBox(
        image = R.drawable.save_earth,
        label = stringResource(R.string.battle_rating),
        sizeBox = sizeBox,
        background = Blue
    ) {
        battle(TypeBattle.Rating)
    }
    ItemActionBox(
        image = R.drawable.brain,
        label = stringResource(R.string.single_battle),
        sizeBox = sizeBox,
        background = Green
    ) {
        battle(TypeBattle.Single)
    }

}