package co.opntest.emarket.data.remote.models.requests

import co.opntest.emarket.domain.models.ProductModel
import com.squareup.moshi.Json

data class ProductRequest(
    @Json(name = "name") val name: String?,
    @Json(name = "price") val price: Double?,
    @Json(name = "imageUrl") val imageUrl: String?
)

fun ProductModel.toRequest(): ProductRequest {
    return ProductRequest(
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun List<ProductModel>.toRequestList(): List<ProductRequest> {
    return map { it.toRequest() }
}
