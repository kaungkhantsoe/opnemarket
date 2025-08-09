package co.opntest.emarket.data.remote.models.responses

import co.opntest.emarket.domain.models.Error
import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String
)

internal fun ErrorResponse.toModel() = Error(message = message)
