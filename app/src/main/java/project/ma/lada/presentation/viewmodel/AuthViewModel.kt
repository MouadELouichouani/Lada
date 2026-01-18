package project.ma.lada.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.ma.lada.LadaApplication
import project.ma.lada.domain.model.User
import project.ma.lada.domain.repository.AuthRepository

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Checking : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Checking)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                if (user != null) {
                    _uiState.value = AuthUiState.Success(user)
                } else {
                    _uiState.value = AuthUiState.Idle
                }
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = authRepository.signInWithGoogle(idToken)
            result.onSuccess { user -> _uiState.value = AuthUiState.Success(user) }.onFailure {
                    error ->
                _uiState.value = AuthUiState.Error(error.message ?: "Google Sign In Failed")
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = authRepository.signInWithEmail(email, password)
            result.onSuccess { user -> _uiState.value = AuthUiState.Success(user) }.onFailure {
                    error ->
                _uiState.value = AuthUiState.Error(error.message ?: "Sign In Failed")
            }
        }
    }

    fun signUpWithEmail(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = authRepository.signUpWithEmail(name, email, password)
            result.onSuccess { user -> _uiState.value = AuthUiState.Success(user) }.onFailure {
                    error ->
                _uiState.value = AuthUiState.Error(error.message ?: "Sign Up Failed")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.value = AuthUiState.Idle
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                                LadaApplication)
                val authRepository = application.container.authRepository
                AuthViewModel(authRepository)
            }
        }
    }
}
