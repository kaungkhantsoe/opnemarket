package co.opntest.emarket.data.remote.models.responses

import co.opntest.emarket.domain.models.ProductModel
import com.squareup.moshi.Json

data class ProductResponse(
    @Json(name = "name") val name: String?,
    @Json(name = "price") val price: Double?,
    @Json(name = "imageUrl") val imageUrl: String?
)

fun ProductResponse.toModel() = ProductModel(
    name = this.name,
    price = this.price,
    imageUrl = this.imageUrl
)

fun List<ProductResponse>.toModelList(): List<ProductModel> {
    return this.map { it.toModel() }
}
