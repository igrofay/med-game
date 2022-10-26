package ru.okei.med.feature.nav_app.model

sealed class RoutingStartContent(
    val route: String
) {
    companion object{
        const val route = "start_routing"
    }
    object Splash: RoutingStartContent("splash")
    object Auth: RoutingStartContent("auth")
}
