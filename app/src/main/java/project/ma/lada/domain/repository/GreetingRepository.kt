package project.ma.lada.domain.repository

import project.ma.lada.core.common.Resource
import project.ma.lada.domain.model.Greeting
import kotlinx.coroutines.flow.Flow

interface GreetingRepository {
    fun getGreeting(): Flow<Resource<Greeting>>
}
