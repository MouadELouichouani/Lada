package project.ma.lada.presentation.state

import project.ma.lada.domain.model.Greeting

data class GreetingState(
    val greeting: Greeting? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
