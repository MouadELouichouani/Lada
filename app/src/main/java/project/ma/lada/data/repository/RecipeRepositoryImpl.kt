package project.ma.lada.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val firestore: FirebaseFirestore
) : RecipeRepository {

    private val recipesCollection = firestore.collection("recipes")

    override fun getAllRecipes(): Flow<List<Recipe>> = callbackFlow {
        val listener = recipesCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val recipes = snapshot.toObjects(Recipe::class.java)
                    trySend(recipes)
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getRecipesByUser(userId: String): Flow<List<Recipe>> = callbackFlow {
        val listener = recipesCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val recipes = snapshot.toObjects(Recipe::class.java)
                    trySend(recipes)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addRecipe(recipe: Recipe): Result<Unit> {
        return try {
            val document = recipesCollection.document()
            val newRecipe = recipe.copy(id = document.id)
            document.set(newRecipe).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateRecipe(recipe: Recipe): Result<Unit> {
        return try {
            recipesCollection.document(recipe.id).set(recipe).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteRecipe(recipeId: String): Result<Unit> {
        return try {
            recipesCollection.document(recipeId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
