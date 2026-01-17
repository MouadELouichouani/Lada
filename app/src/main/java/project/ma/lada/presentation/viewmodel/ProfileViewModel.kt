package project.ma.lada.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import project.ma.lada.LadaApplication
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.model.UserProfile
import project.ma.lada.domain.repository.AuthRepository
import project.ma.lada.domain.repository.ProfileRepository

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(
            val userProfile: UserProfile,
            val recipes: List<Recipe>,
            val isCurrentUser: Boolean
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

class ProfileViewModel(
        private val profileRepository: ProfileRepository,
        private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile(targetUid: String? = null) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val currentUser = authRepository.currentUser.firstOrNull() ?: return@launch
            val uidToLoad = targetUid ?: currentUser?.uid

            if (uidToLoad == null) {
                _uiState.value = ProfileUiState.Error("User not logged in and no profile specified")
                return@launch
            }

            val profileResult = profileRepository.getUserProfile(uidToLoad)
            val recipeResult = profileRepository.getRecipesForUser(uidToLoad)

            if (profileResult.isSuccess) {
                val profile = profileResult.getOrThrow()
                val recipes = recipeResult.getOrDefault(emptyList())
                val isCurrentUser = currentUser.uid == uidToLoad

                _uiState.value =
                        ProfileUiState.Success(
                                userProfile = profile,
                                recipes = recipes,
                                isCurrentUser = isCurrentUser
                        )
            } else {
                val exception = profileResult.exceptionOrNull()
                if (exception?.message == "User profile not found" && currentUser.uid == uidToLoad
                ) {
                    // Auto-create profile for current user
                    val newProfile =
                            UserProfile(
                                    uid = currentUser.uid,
                                    displayName = currentUser.displayName ?: "User",
                                    photoUrl = currentUser.photoUrl
                            )
                    val createResult = profileRepository.createOrUpdateProfile(newProfile)
                    if (createResult.isSuccess) {
                        _uiState.value =
                                ProfileUiState.Success(
                                        userProfile = newProfile,
                                        recipes = emptyList(),
                                        isCurrentUser = true
                                )
                    } else {
                        _uiState.value =
                                ProfileUiState.Error(
                                        "Failed to create profile: ${createResult.exceptionOrNull()?.message}"
                                )
                    }
                } else {
                    _uiState.value =
                            ProfileUiState.Error("Failed to load profile: ${exception?.message}")
                }
            }
        }
    }

    fun toggleFollow(targetUid: String) {
        viewModelScope.launch {
            val currentUser = authRepository.currentUser.firstOrNull() ?: return@launch
            val result = profileRepository.toggleFollow(currentUser.uid, targetUid)
            if (result.isSuccess) {
                loadProfile(targetUid)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                                LadaApplication)
                val profileRepository = application.container.profileRepository
                val authRepository = application.container.authRepository
                ProfileViewModel(profileRepository, authRepository)
            }
        }
    }
}
