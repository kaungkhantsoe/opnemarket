package co.opntest.emarket.ui.screens.main

import co.opntest.emarket.ui.base.BaseAppDestination
import co.opntest.emarket.ui.models.ProductUiModel

const val SelectedProductListKey = "SelectedProductListKey"

sealed class MainDestination {

    object Home : BaseAppDestination("home")

    object Summary : BaseAppDestination("summary") {
        fun addParcel(value: List<ProductUiModel>) = apply {
            parcelableArgument = SelectedProductListKey to value
        }
    }
}
