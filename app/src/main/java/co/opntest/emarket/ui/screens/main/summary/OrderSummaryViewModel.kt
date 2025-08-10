package co.opntest.emarket.ui.screens.main.summary

import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.ProductUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderSummaryViewModel : BaseViewModel() {

    private val _products = MutableStateFlow<List<ProductUiModel>>(emptyList())
    val products = _products.asStateFlow()

    fun setProducts(products: List<ProductUiModel>) {
        _products.value = products
    }

    fun getTotalPrice(): Double {
        return products.value.sumOf { it.price * it.selectedCount }
    }
}
