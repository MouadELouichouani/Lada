package project.ma.lada.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.model.UserProfile
import project.ma.lada.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val firestore: FirebaseFirestore) : ProfileRepository {

    private val usersCollection = firestore.collection("users")
    private val recipesCollection = firestore.collection("recipes")

    override suspend fun getUserProfile(uid: String): Result<UserProfile> {
        return try {
            val document = usersCollection.document(uid).get().await()
            if (document.exists()) {
                val userProfile = document.toObject(UserProfile::class.java)
                if (userProfile != null) {
                    Result.success(userProfile)
                } else {
                    Result.failure(Exception("Failed to parse user profile"))
                }
            } else {
                // If user doesn't exist in 'users' collection yet (old user), return default/empty
                // based on Auth?
                // For now, let's treat it as failure or return a basic profile if we had auth info
                // here.
                // Better approach: ViewModel handles "Auth Success" -> "Fetch Profile" -> "If
                // missing, create default"
                Result.failure(Exception("User profile not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFollow(currentUid: String, targetUid: String): Result<Unit> {
        return try {
            firestore
                    .runTransaction { transaction ->
                        val currentUserRef = usersCollection.document(currentUid)
                        val targetUserRef = usersCollection.document(targetUid)

                        val currentUserSnapshot = transaction.get(currentUserRef)

                        val following =
                                currentUserSnapshot.get("following") as? List<String> ?: emptyList()

                        if (following.contains(targetUid)) {
                            // Unfollow
                            transaction.update(
                                    currentUserRef,
                                    "following",
                                    FieldValue.arrayRemove(targetUid)
                            )
                            transaction.update(
                                    targetUserRef,
                                    "followers",
                                    FieldValue.arrayRemove(currentUid)
                            )
                        } else {
                            // Follow
                            transaction.update(
                                    currentUserRef,
                                    "following",
                                    FieldValue.arrayUnion(targetUid)
                            )
                            transaction.update(
                                    targetUserRef,
                                    "followers",
                                    FieldValue.arrayUnion(currentUid)
                            )
                        }
                    }
                    .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipesForUser(uid: String): Result<List<Recipe>> {
        return try {
            val snapshot = recipesCollection.whereEqualTo("userId", uid).get().await()
            val recipes = snapshot.toObjects(Recipe::class.java)
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createOrUpdateProfile(userProfile: UserProfile): Result<Unit> {
        return try {
            usersCollection.document(userProfile.uid).set(userProfile).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
