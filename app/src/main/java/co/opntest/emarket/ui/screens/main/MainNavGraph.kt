package co.opntest.emarket.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import co.opntest.emarket.extensions.composable
import co.opntest.emarket.ui.AppDestination
import co.opntest.emarket.ui.screens.main.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.Home.destination
    ) {
        composable(MainDestination.Home) {
            HomeScreen()
        }
    }
}
