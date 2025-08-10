package co.opntest.emarket.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import co.opntest.emarket.domain.usecases.GetProductListUseCase
import co.opntest.emarket.domain.usecases.GetStoreDetailUseCase
import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.models.StoreDetailUiModel
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.ui.models.toUiModelList
import co.opntest.emarket.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val getProductListUseCase: GetProductListUseCase,
) : BaseViewModel() {

    private val _storeDetail = MutableStateFlow<StoreDetailUiModel?>(null)
    val storeDetail = _storeDetail.asStateFlow()

    private val _products = MutableStateFlow<List<ProductUiModel>>(emptyList())
    val products = _products.asStateFlow()

    init {
        getStoreDetail()
        getProductList()
    }

    fun getStoreDetail() {
        getStoreDetailUseCase()
            .injectLoading()
            .onEach { result ->
                _storeDetail.emit(result.toUiModel())
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

    fun getProductList() {
        getProductListUseCase()
            .injectLoading()
            .onEach { result ->
                _products.emit(result.toUiModelList())
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

    fun updateProductCount(count: Int, product: ProductUiModel) {
        val updatedProducts = _products.value.map {
            if (it.name == product.name) {
                it.copy(selectedCount = count)
            } else {
                it
            }
        }
        _products.value = updatedProducts
    }
}
