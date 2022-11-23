package ru.okei.med.feature.nav_app.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.okei.med.feature.battle_screen.view.BattleScreen
import ru.okei.med.feature.friends.view.FriendsScreen
import ru.okei.med.feature.main_screen.view.MainScreen
import ru.okei.med.feature.nav_app.model.RoutingMainContent
import ru.okei.med.feature.profile_screen.view.ProfileScreen


fun NavGraphBuilder.navMainContent(
    navController: NavController,
) {
    navigation(startDestination = RoutingMainContent.Main.route, route = RoutingMainContent.route) {
        composable(RoutingMainContent.Main.route){
            MainScreen(
                layerBattles = { type->
                    navController.navigate(RoutingMainContent.Battle.argRoute(type)){
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                battleRating = {type->
                    navController.navigate(RoutingMainContent.Battle.argRoute(type)){
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                editProfile = {
                    navController.navigate(RoutingMainContent.EditProfile.route)
                },
                openFriend = {
                    navController.navigate(RoutingMainContent.Friends.route)
                }
            )
        }
        composable(RoutingMainContent.Battle.argRoute()){
            BattleScreen{
                navController.popBackStack()
            }
        }
        composable(RoutingMainContent.EditProfile.route){
            ProfileScreen()
        }
        composable(RoutingMainContent.Friends.route){
            FriendsScreen()
        }
    }
}