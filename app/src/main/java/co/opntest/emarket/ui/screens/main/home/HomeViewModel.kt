package co.opntest.emarket.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import co.opntest.emarket.domain.usecases.GetStoreDetailUseCase
import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.models.StoreDetailUiModel
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
) : BaseViewModel() {

    private val _storeDetail = MutableStateFlow<StoreDetailUiModel?>(null)
    val storeDetail = _storeDetail.asStateFlow()

    private val _products = MutableStateFlow<List<ProductUiModel>>(emptyList())
    val products = _products.asStateFlow()

    init {
        getStoreDetail()
        _products.value = listOf(
            ProductUiModel(
                name = "Latte",
                price = 50.0,
                imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
            ),
            ProductUiModel(
                name = "Dark Tiramisu Mocha",
                price = 75.0,
                imageUrl = "https://www.nespresso.com/shared_res/mos/free_html/sg/b2b/b2ccoffeerecipes/listing-image/image/dark-tiramisu-mocha.jpg",
            ),
        )
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
}
