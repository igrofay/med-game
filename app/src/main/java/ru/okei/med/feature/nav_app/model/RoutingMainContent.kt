package ru.okei.med.feature.nav_app.model

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
        fun argRoute(
            typeBattle: String = "{typeBattle}",
            emailFriend: String? = "{emailFriend}",
            tokenRoom: String? = "{tokenRoom}",
        )= "$route/$typeBattle?emailFriend=$emailFriend&tokenRoom=$tokenRoom"
    }
    object EditProfile: RoutingMainContent(
        route = "edit_profile"
    )
    object Friends : RoutingMainContent(
        route = "friends"
    )
    object Rating: RoutingMainContent(
        route = "rating"
    )
}