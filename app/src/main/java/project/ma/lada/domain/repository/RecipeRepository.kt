package project.ma.lada.domain.repository

import kotlinx.coroutines.flow.Flow
import project.ma.lada.domain.model.Recipe

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun getRecipesByUser(userId: String): Flow<List<Recipe>>
    suspend fun addRecipe(recipe: Recipe): Result<Unit>
    suspend fun updateRecipe(recipe: Recipe): Result<Unit>
    suspend fun deleteRecipe(recipeId: String): Result<Unit>
}
