package project.ma.lada.data.repository

import project.ma.lada.core.common.Resource
import project.ma.lada.data.source.GreetingDataSource
import project.ma.lada.domain.model.Greeting
import project.ma.lada.domain.repository.GreetingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GreetingRepositoryImpl(
    private val dataSource: GreetingDataSource
) : GreetingRepository {
    override fun getGreeting(): Flow<Resource<Greeting>> = flow {
        emit(Resource.Loading())
        try {
            val greeting = dataSource.fetchGreeting()
            emit(Resource.Success(greeting))
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred: ${e.message}"))
        }
    }
}
