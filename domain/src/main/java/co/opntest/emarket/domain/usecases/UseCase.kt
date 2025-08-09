package co.opntest.emarket.domain.usecases

import co.opntest.emarket.domain.models.Model
import co.opntest.emarket.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getModels()
    }
}
