package ru.okei.med.feature.nav_app.model

import androidx.annotation.DrawableRes
import ru.okei.med.R

sealed class RoutingMainContent(
    val route: String,
) {
    companion object{
        const val route = "main_routing"
    }
    object Main : RoutingMainContent(
        route = "main"
    )
    object Battle :  RoutingMainContent(
        route = "battle"
    ){
        fun argRoute(typeBattle: String = "{typeBattle}")= "$route/$typeBattle"
    }
    object EditProfile: RoutingMainContent(
        route = "edit_profile"
    )
    object Friends : RoutingMainContent(
        route = "friends"
    )
}