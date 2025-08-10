package co.opntest.emarket.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import co.opntest.emarket.extensions.composable
import co.opntest.emarket.extensions.getThenRemove
import co.opntest.emarket.extensions.navigateTo
import co.opntest.emarket.ui.AppDestination
import co.opntest.emarket.ui.base.BaseAppDestination
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.screens.main.home.HomeScreen
import co.opntest.emarket.ui.screens.main.success.OrderSuccessScreen
import co.opntest.emarket.ui.screens.main.summary.OrderSummaryScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.Home.destination
    ) {
        composable(MainDestination.Home) { backStackEntry ->
            val isOrderSuccess =
                backStackEntry.savedStateHandle.getThenRemove<Boolean>(IsOrderSuccessKey) ?: false

            HomeScreen(
                isOrderSuccess = isOrderSuccess,
                onClickViewOrder = { products ->
                    navController.navigateTo(MainDestination.Summary.addParcel(products))
                }
            )
        }

        composable(MainDestination.Summary) {
            val selectedProducts = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<List<ProductUiModel>>(SelectedProductListKey)

            OrderSummaryScreen(
                productList = selectedProducts.orEmpty(),
                onOrderSuccess = {
                    navController.navigateTo(MainDestination.OrderSuccess) {
                        popUpTo(MainDestination.Home.route) {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable(MainDestination.OrderSuccess) {
            OrderSuccessScreen(
                onClickContinueShopping = {
                    navController.navigateTo(BaseAppDestination.Up().apply {
                        put(IsOrderSuccessKey, true)
                    })
                }
            )
        }
    }
}
