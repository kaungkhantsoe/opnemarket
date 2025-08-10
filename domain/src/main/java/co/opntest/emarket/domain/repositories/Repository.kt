package co.opntest.emarket.domain.repositories

import co.opntest.emarket.domain.models.Model
import co.opntest.emarket.domain.models.ProductModel
import co.opntest.emarket.domain.models.StoreDetailModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    // TODO Remove this later
    fun getModels(): Flow<List<Model>>

    fun getStoreDetail(): Flow<StoreDetailModel>

    fun getProductList(): Flow<List<ProductModel>>
}
