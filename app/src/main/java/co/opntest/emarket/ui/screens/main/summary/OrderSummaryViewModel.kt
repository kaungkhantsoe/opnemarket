package co.opntest.emarket.ui.screens.main.summary

import androidx.lifecycle.viewModelScope
import co.opntest.emarket.domain.models.PlaceOrderModel
import co.opntest.emarket.domain.usecases.PlaceOrderUseCase
import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.models.toDomainModel
import co.opntest.emarket.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderSummaryViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val placeOrderUseCase: PlaceOrderUseCase,
) : BaseViewModel() {

    private val _products = MutableStateFlow<List<ProductUiModel>>(emptyList())
    val products = _products.asStateFlow()

    private val _onPlaceOrderSuccess = MutableSharedFlow<Unit>()
    val onPlaceOrderSuccess = _onPlaceOrderSuccess.asSharedFlow()

    fun setProducts(products: List<ProductUiModel>) {
        _products.value = products
    }

    fun getTotalPrice(): Double {
        return products.value.sumOf { it.price * it.selectedCount }
    }

    fun placeOrder(deliveryAddress: String) {
        val order = PlaceOrderModel(
            products = products.value.flatMap { productUiModel ->
                List(productUiModel.selectedCount) { productUiModel.toDomainModel() }
            },
            deliveryAddress = deliveryAddress
        )
        placeOrderUseCase(order)
            .injectLoading()
            .onEach {
                _onPlaceOrderSuccess.emit(Unit)
            }
            .catch { e -> _error.emit(e) }
            .flowOn(dispatchersProvider.io)
            .launchIn(viewModelScope)
    }
}
