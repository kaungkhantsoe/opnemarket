package co.opntest.emarket.domain.usecases

import co.opntest.emarket.domain.models.StoreDetailModel
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreDetailUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<StoreDetailModel> {
        return repository.getStoreDetail()
    }
}
