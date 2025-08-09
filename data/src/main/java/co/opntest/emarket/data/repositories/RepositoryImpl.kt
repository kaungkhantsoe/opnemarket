package co.opntest.emarket.data.repositories

import co.opntest.emarket.data.extensions.flowTransform
import co.opntest.emarket.data.remote.models.responses.toModels
import co.opntest.emarket.data.remote.services.ApiService
import co.opntest.emarket.domain.models.Model
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override fun getModels(): Flow<List<Model>> = flowTransform {
        apiService.getResponses().toModels()
    }
}
