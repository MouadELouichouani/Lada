package project.ma.lada.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.ma.lada.LadaApplication
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.repository.RecipeRepository

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}

class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipeUiState: StateFlow<RecipeUiState> = recipeRepository.getAllRecipes()
        .map { RecipeUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeUiState.Loading
        )

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.addRecipe(recipe)
        }
    }

    fun deleteRecipe(recipeId: String) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipeId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LadaApplication)
                val recipeRepository = application.container.recipeRepository
                RecipeViewModel(recipeRepository)
            }
        }
    }
}
