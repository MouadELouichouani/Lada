package project.ma.lada.data.source

import project.ma.lada.domain.model.Greeting
import kotlinx.coroutines.delay

class GreetingDataSource {
    suspend fun fetchGreeting(): Greeting {
        delay(1000) // Simulate network delay
        return Greeting(message = "Hello from Clean Architecture!")
    }
}
