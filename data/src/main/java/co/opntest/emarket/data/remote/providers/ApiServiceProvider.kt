package co.opntest.emarket.data.remote.providers

import co.opntest.emarket.data.remote.services.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
