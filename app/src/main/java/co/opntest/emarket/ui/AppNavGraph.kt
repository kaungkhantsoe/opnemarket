package co.opntest.emarket.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import co.opntest.emarket.ui.screens.main.mainNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = AppDestination.MainNavGraph.destination
    ) {
        mainNavGraph(navController = navController)
    }
}
