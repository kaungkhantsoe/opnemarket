package co.opntest.emarket.data.remote.models.responses

import co.opntest.emarket.domain.models.Model
import com.squareup.moshi.Json

// TODO Remove this later
data class Response(
    @Json(name = "id") val id: Int?
)

private fun Response.toModel() = Model(id = this.id)

fun List<Response>.toModels() = this.map { it.toModel() }
