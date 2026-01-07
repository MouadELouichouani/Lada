package project.ma.lada.domain.repository

import kotlinx.coroutines.flow.Flow
import project.ma.lada.domain.model.User

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun signInWithGoogle(idToken: String): Result<User>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signUpWithEmail(name: String, email: String, password: String): Result<User>
    suspend fun signOut()
}
