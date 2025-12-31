package project.ma.lada.di

import project.ma.lada.data.repository.GreetingRepositoryImpl
import project.ma.lada.data.source.GreetingDataSource
import project.ma.lada.domain.repository.GreetingRepository
import project.ma.lada.domain.usecase.GetGreetingUseCase

interface AppContainer {
    val greetingRepository: GreetingRepository
    val getGreetingUseCase: GetGreetingUseCase
}

class AppDataContainer : AppContainer {
    private val greetingDataSource = GreetingDataSource()
    
    override val greetingRepository: GreetingRepository by lazy {
        GreetingRepositoryImpl(greetingDataSource)
    }

    override val getGreetingUseCase: GetGreetingUseCase by lazy {
        GetGreetingUseCase(greetingRepository)
    }
}
