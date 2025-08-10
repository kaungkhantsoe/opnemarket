package co.opntest.emarket.ui.screens.main.summary

import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.ProductUiModel

class OrderSummaryViewModel : BaseViewModel() {

    val products: List<ProductUiModel> = listOf(
        ProductUiModel(
            name = "Product 1",
            price = 10.0,
            imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
            selectedCount = 2
        ),
        ProductUiModel(
            name = "Product 2",
            price = 10.0,
            imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
            selectedCount = 2
        )
    )
}
