package ru.okei.med.feature.nav_app.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import ru.okei.med.domain.model.TypeBattle
import ru.okei.med.feature.battle_screen.view.BattleScreen
import ru.okei.med.feature.friends.view.FriendsScreen
import ru.okei.med.feature.main_screen.view.MainScreen
import ru.okei.med.feature.nav_app.model.RoutingMainContent
import ru.okei.med.feature.profile_screen.view.ProfileScreen
import ru.okei.med.feature.rating.view.RatingScreen


fun NavGraphBuilder.navMainContent(
    navController: NavController,
) {
    navigation(startDestination = RoutingMainContent.Main.route, route = RoutingMainContent.route) {
        composable(RoutingMainContent.Main.route) {
            MainScreen(
                layerBattles = { type ->
                    navController.navigate(RoutingMainContent.Battle.argRoute(type, null, null)) {
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                editProfile = {
                    navController.navigate(RoutingMainContent.EditProfile.route) {
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                openFriend = {
                    navController.navigate(RoutingMainContent.Friends.route) {
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                openRating = {
                    navController.navigate(RoutingMainContent.Rating.route) {
                        popUpTo(RoutingMainContent.Main.route)
                    }
                },
                openFightWithFriend = {tokenRoom->
                    navController.navigate(RoutingMainContent.Battle.argRoute(
                        TypeBattle.WithFriend.name,
                        null,
                        tokenRoom
                    )) {
                        popUpTo(RoutingMainContent.Main.route)
                    }
                }
            )
        }
        composable(
            RoutingMainContent.Battle.argRoute(),
            arguments = listOf(
                navArgument("typeBattle") { type = NavType.StringType },
                navArgument("emailFriend") { type = NavType.StringType },
                navArgument("tokenRoom") { type = NavType.StringType}
            )
        ) {
            BattleScreen {
                navController.popBackStack()
            }
        }
        composable(RoutingMainContent.EditProfile.route) {
            ProfileScreen()
        }
        composable(RoutingMainContent.Friends.route) {
            FriendsScreen { email ->
                navController.navigate(
                    RoutingMainContent.Battle.argRoute(
                        TypeBattle.WithFriend.name,
                        email,
                        null
                    )
                ) {
                    popUpTo(RoutingMainContent.Friends.route)
                }
            }
        }
        composable(RoutingMainContent.Rating.route) {
            RatingScreen()
        }
    }
}