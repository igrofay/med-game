package ru.okei.med.feature.nav_app.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.okei.med.feature.nav_app.model.RoutingMainContent
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.widget.noRippleClickable

@Composable
fun LeftNavBar(
    navController:NavController,
    listRoute: List<RoutingMainContent>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var positionClamp by remember { mutableStateOf(Offset.Zero) }
    val animatePositionClamp by animateOffsetAsState(
        targetValue = positionClamp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(White95.copy(0.2f))
            .padding(end = 1.dp)
            .width(IntrinsicSize.Min)
            .background(colors.background)
    ){
        Row(
            modifier = Modifier
                .offset(
                    y = with(LocalDensity.current) { animatePositionClamp.y.toDp() } - 10.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier
                .height(50.dp)
                .width(5.dp)
                .background(
                    colors.secondary,
                    RoundedCornerShape(
                        bottomEndPercent = 100,
                        topEndPercent = 100
                    )
                ))
            Box(modifier = Modifier
                .height(50.dp)
                .width(5.dp)
                .background(
                    colors.secondary,
                    RoundedCornerShape(
                        topStartPercent = 100,
                        bottomStartPercent = 100
                    )
                ))
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
//            listRoute.forEach { screen->
//                ItemBottomBar(
//                    currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                    onTab = {
//
//                        navController.navigate(screen.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    },
//                    setPosition = {
//                        positionClamp = it
//                    },
//                    painterResource(screen.selectIcon),
//                    painterResource(screen.passiveIcon)
//                )
//            }
        }
    }
}


@Composable
fun BottomNavBar(
    navController:NavController,
    listRoute: List<RoutingMainContent>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var positionClamp by remember { mutableStateOf(Offset.Zero) }
    val animatePositionClamp by animateOffsetAsState(
        targetValue = positionClamp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White95.copy(0.2f))
            .padding(top = 1.dp)
            .height(IntrinsicSize.Min)
            .background(colors.background)
    ){
        Column(
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { animatePositionClamp.x.toDp() } - 10.dp
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(modifier = Modifier
                .height(5.dp)
                .width(50.dp)
                .background(
                    colors.secondary,
                    RoundedCornerShape(
                        bottomEndPercent = 100,
                        bottomStartPercent = 100
                    )
                ))
            Box(modifier = Modifier
                    .height(5.dp)
                    .width(50.dp)
                    .background(
                        colors.secondary,
                        RoundedCornerShape(
                            topStartPercent = 100,
                            topEndPercent = 100
                        )
                    ))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
//            listRoute.forEach { screen->
//                ItemBottomBar(
//                    currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                    onTab = {
//                        navController.navigate(screen.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    },
//                    setPosition = {
//                        positionClamp = it
//                    },
//                    painterResource(screen.selectIcon),
//                    painterResource(screen.passiveIcon)
//                )
//            }
        }
    }
}

@Composable
private fun ItemBottomBar(
    isSelected:Boolean,
    onTab:()->Unit,
    setPosition:(Offset)->Unit,
    selectedIcon:Painter,
    passiveIcon: Painter
) {
    var positionItem by remember { mutableStateOf(Offset.Zero) }
    Icon(
        painter = if (isSelected)
            selectedIcon
        else passiveIcon,
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .onGloballyPositioned { coordinates ->
                positionItem = coordinates.positionInRoot()
            }
            .noRippleClickable(onClick = onTab),
        tint = colors.secondary
    )
    LaunchedEffect(isSelected){
        if (isSelected){
            setPosition(positionItem)
        }
    }
}