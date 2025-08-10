package co.opntest.emarket.data.repositories

import co.opntest.emarket.data.extensions.flowTransform
import co.opntest.emarket.data.remote.models.requests.toRequest
import co.opntest.emarket.data.remote.models.responses.toModel
import co.opntest.emarket.data.remote.models.responses.toModelList
import co.opntest.emarket.data.remote.services.ApiService
import co.opntest.emarket.domain.models.Model
import co.opntest.emarket.domain.models.PlaceOrderModel
import co.opntest.emarket.domain.models.ProductModel
import co.opntest.emarket.domain.models.StoreDetailModel
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    // TODO Remove this later
    override fun getModels(): Flow<List<Model>> = flowTransform {
        emptyList()
    }

    override fun getStoreDetail(): Flow<StoreDetailModel> = flowTransform {
        apiService.getStoreDetail().toModel()
    }

    override fun getProductList(): Flow<List<ProductModel>> = flowTransform {
        apiService.getProductList().toModelList()
    }

    override fun placeOrder(placeOrderModel: PlaceOrderModel): Flow<Unit> = flowTransform{
        apiService.placeOrder(placeOrderModel.toRequest())
    }
}
