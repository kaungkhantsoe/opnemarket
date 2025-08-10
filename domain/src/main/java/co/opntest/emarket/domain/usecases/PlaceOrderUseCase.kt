package co.opntest.emarket.domain.usecases

import co.opntest.emarket.domain.models.PlaceOrderModel
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(placeOrderModel: PlaceOrderModel): Flow<Unit> {
        return repository.placeOrder(placeOrderModel)
    }
}
