package co.opntest.emarket.data.remote.models.requests

import co.opntest.emarket.domain.models.PlaceOrderModel
import com.squareup.moshi.Json

data class PlaceOrderRequest(
    @Json(name = "products") val products: List<ProductRequest>,
    @Json(name = "delivery_address") val deliveryAddress: String,
)

fun PlaceOrderModel.toRequest(): PlaceOrderRequest {
    return PlaceOrderRequest(
        products = products.toRequestList(),
        deliveryAddress = deliveryAddress,
    )
}
