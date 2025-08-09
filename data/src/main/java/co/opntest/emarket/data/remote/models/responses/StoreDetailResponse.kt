package co.opntest.emarket.data.remote.models.responses

import co.opntest.emarket.domain.models.StoreDetailModel
import com.squareup.moshi.Json
import java.time.LocalTime

data class StoreDetailResponse(
    @Json(name= "name") val name: String?,
    @Json(name = "rating") val rating: Double?,
    @Json(name = "openingTime") val openingTime: LocalTime?,
    @Json(name = "closingTime") val closingTime: LocalTime?,
)

fun StoreDetailResponse.toModel(): StoreDetailModel {
    return StoreDetailModel(
        name = this.name,
        rating = this.rating,
        openingTime = this.openingTime,
        closingTime = this.closingTime
    )
}
