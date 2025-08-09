package co.opntest.emarket.domain.repositories

import co.opntest.emarket.domain.models.Model
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getModels(): Flow<List<Model>>
}
