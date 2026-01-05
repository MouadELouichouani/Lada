package project.ma.lada.di

import project.ma.lada.data.repository.GreetingRepositoryImpl
import project.ma.lada.data.source.GreetingDataSource
import project.ma.lada.domain.repository.GreetingRepository
import project.ma.lada.domain.usecase.GetGreetingUseCase
import project.ma.lada.domain.repository.AuthRepository
import project.ma.lada.data.repository.AuthRepositoryImpl

interface AppContainer {
    val greetingRepository: GreetingRepository
    val getGreetingUseCase: GetGreetingUseCase
    val authRepository: AuthRepository
}

class AppDataContainer : AppContainer {
    private val greetingDataSource = GreetingDataSource()
    
    override val greetingRepository: GreetingRepository by lazy {
        GreetingRepositoryImpl(greetingDataSource)
    }

    override val getGreetingUseCase: GetGreetingUseCase by lazy {
        GetGreetingUseCase(greetingRepository)
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(com.google.firebase.auth.FirebaseAuth.getInstance())
    }
}
