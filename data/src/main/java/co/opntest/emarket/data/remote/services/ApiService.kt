package co.opntest.emarket.data.remote.services

import co.opntest.emarket.data.remote.models.requests.PlaceOrderRequest
import co.opntest.emarket.data.remote.models.responses.ProductResponse
import co.opntest.emarket.data.remote.models.responses.Response
import co.opntest.emarket.data.remote.models.responses.StoreDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // TODO Remove this later
    @GET("users")
    suspend fun getResponses(): List<Response>

    @GET("storeInfo")
    suspend fun getStoreDetail(): StoreDetailResponse

    @GET("products")
    suspend fun getProductList(): List<ProductResponse>

    @POST("order")
    suspend fun placeOrder(
        @Body placeOrderRequest: PlaceOrderRequest,
    )
}
