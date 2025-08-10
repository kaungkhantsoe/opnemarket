package co.opntest.emarket.domain.usecases

import co.opntest.emarket.domain.models.ProductModel
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<ProductModel>> {
        return repository.getProductList()
    }
}
