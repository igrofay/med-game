package ru.okei.med.feature.nav_app.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.okei.med.feature.nav_app.model.RoutingMainContent
import ru.okei.med.feature.nav_app.model.RoutingStartContent

@Composable
fun InitialNavigationApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = RoutingStartContent.route) {
        navStart(
            navController = nav,
            leaveNavStart = {
                nav.navigate(RoutingMainContent.route){
                    popUpTo(RoutingStartContent.route){ inclusive = true }
                }
            }
        )
        navMainContent(
            navController = nav,
        )
    }
}