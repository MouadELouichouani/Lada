package project.ma.lada.domain.repository

import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(uid: String): Result<UserProfile>
    suspend fun toggleFollow(currentUid: String, targetUid: String): Result<Unit>
    suspend fun getRecipesForUser(uid: String): Result<List<Recipe>>
    suspend fun createOrUpdateProfile(userProfile: UserProfile): Result<Unit>
}
