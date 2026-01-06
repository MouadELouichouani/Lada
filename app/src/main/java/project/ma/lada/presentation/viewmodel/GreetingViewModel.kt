package project.ma.lada.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project.ma.lada.core.common.Resource
import project.ma.lada.domain.usecase.GetGreetingUseCase
import project.ma.lada.presentation.state.GreetingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import project.ma.lada.LadaApplication

class GreetingViewModel(
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GreetingState())
    val state: StateFlow<GreetingState> = _state

    init {
        getGreeting()
    }

    private fun getGreeting() {
        getGreetingUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GreetingState(greeting = result.data)
                }
                is Resource.Error -> {
                    _state.value = GreetingState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = GreetingState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LadaApplication)
                GreetingViewModel(application.container.getGreetingUseCase)
            }
        }
    }
}
