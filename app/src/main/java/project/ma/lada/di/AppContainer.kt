package project.ma.lada.di

import project.ma.lada.data.repository.AuthRepositoryImpl
import project.ma.lada.data.repository.GreetingRepositoryImpl
import project.ma.lada.data.repository.RecipeRepositoryImpl
import project.ma.lada.data.source.GreetingDataSource
import project.ma.lada.domain.repository.AuthRepository
import project.ma.lada.domain.repository.GreetingRepository
import project.ma.lada.domain.repository.ProfileRepository
import project.ma.lada.domain.repository.RecipeRepository
import project.ma.lada.domain.usecase.GetGreetingUseCase

interface AppContainer {
    val greetingRepository: GreetingRepository
    val getGreetingUseCase: GetGreetingUseCase
    val authRepository: AuthRepository
    val recipeRepository: RecipeRepository
    val profileRepository: ProfileRepository
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

    override val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(com.google.firebase.firestore.FirebaseFirestore.getInstance())
    }

    override val profileRepository: ProfileRepository by lazy {
        project.ma.lada.data.repository.ProfileRepositoryImpl(
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
        )
    }
}
