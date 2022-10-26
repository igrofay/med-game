package ru.okei.med.feature.nav_app.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.okei.med.feature.auth_screen.view.AuthScreen
import ru.okei.med.feature.nav_app.model.RoutingStartContent
import ru.okei.med.feature.splash_screen.view.SplashScreen

fun NavGraphBuilder.navStart(
    navController: NavController,
    leaveNavStart: ()->Unit
) {
    navigation(startDestination = RoutingStartContent.Splash.route, route = RoutingStartContent.route) {
        composable(route = RoutingStartContent.Splash.route) {
            SplashScreen(
                sessionCreated = leaveNavStart,
                sessionNotCreated = {
                    navController.navigate(RoutingStartContent.Auth.route){
                        popUpTo(RoutingStartContent.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(RoutingStartContent.Auth.route){
            AuthScreen(
                loggedIn = leaveNavStart
            )
        }
    }
}