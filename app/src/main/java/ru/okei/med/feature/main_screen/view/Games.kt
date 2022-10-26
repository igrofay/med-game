package ru.okei.med.feature.main_screen.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import ru.okei.med.R
import ru.okei.med.feature.theme.Blue
import ru.okei.med.feature.theme.Purple

@Composable
fun Games(
    sizeBox: DpSize,
    layerBattles:()->Unit,
    battleRating:()->Unit
) {
    ItemActionBox(
        image = R.drawable.trophy,
        label = stringResource(R.string.layer_battles),
        sizeBox = sizeBox,
        background = Purple
    ) {
        layerBattles()
    }
    ItemActionBox(
        image = R.drawable.save_earth,
        label = stringResource(R.string.battle_rating),
        sizeBox = sizeBox,
        background = Blue
    ) {
        battleRating()
    }
}