package project.ma.lada.domain.model

import com.google.firebase.Timestamp

data class Recipe(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val imageUrl: String? = null,
    val userId: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
