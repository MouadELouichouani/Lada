package project.ma.lada.domain.usecase

import project.ma.lada.core.common.Resource
import project.ma.lada.domain.model.Greeting
import project.ma.lada.domain.repository.GreetingRepository
import kotlinx.coroutines.flow.Flow

class GetGreetingUseCase(private val repository: GreetingRepository) {
    operator fun invoke(): Flow<Resource<Greeting>> {
        return repository.getGreeting()
    }
}
