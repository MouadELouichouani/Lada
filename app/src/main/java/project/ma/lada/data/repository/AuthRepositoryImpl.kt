package project.ma.lada.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import project.ma.lada.domain.model.User
import project.ma.lada.domain.repository.AuthRepository

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener =
                FirebaseAuth.AuthStateListener { auth -> trySend(auth.currentUser?.toDomain()) }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val user =
                    authResult.user?.toDomain()
                            ?: return Result.failure(Exception("User not found after sign in"))
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user =
                    authResult.user?.toDomain()
                            ?: return Result.failure(Exception("User not found after sign in"))
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmail(
            name: String,
            email: String,
            password: String
    ): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser =
                    authResult.user ?: return Result.failure(Exception("User not created"))

            // Update profile with name
            val profileUpdates =
                    com.google.firebase.auth.userProfileChangeRequest { displayName = name }
            firebaseUser.updateProfile(profileUpdates).await()

            Result.success(firebaseUser.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    private fun com.google.firebase.auth.FirebaseUser.toDomain(): User {
        return User(
                uid = uid,
                email = email,
                displayName = displayName,
                photoUrl = photoUrl?.toString()
        )
    }
}
