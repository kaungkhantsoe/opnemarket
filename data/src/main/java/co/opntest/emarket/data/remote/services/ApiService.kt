package co.opntest.emarket.data.remote.services

import co.opntest.emarket.data.remote.models.responses.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getResponses(): List<Response>
}
