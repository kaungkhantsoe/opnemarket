package co.opntest.emarket.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import co.opntest.emarket.domain.models.StoreDetailModel
import co.opntest.emarket.domain.usecases.UseCase
import co.opntest.emarket.ui.base.BaseViewModel
import co.opntest.emarket.ui.models.StoreDetailUiModel
import co.opntest.emarket.ui.models.UiModel
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    useCase: UseCase,
) : BaseViewModel() {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels = _uiModels.asStateFlow()

    private val _storeDetail = MutableStateFlow<StoreDetailUiModel?>(null)
    val storeDetail = _storeDetail.asStateFlow()

    init {
        useCase()
            .injectLoading()
            .onEach { result ->
                val uiModels = result.map { it.toUiModel() }
                _uiModels.emit(uiModels)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)

        _storeDetail.value = StoreDetailModel(
            name = "The Coffee Shop",
            rating = 4.6,
            openingTime = LocalTime.of(15, 1, 1),
            closingTime = LocalTime.of(19, 45, 51)
        ).toUiModel()
    }
}
