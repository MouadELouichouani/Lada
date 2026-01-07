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
    val rating: Double = 0.0,
    val time: String = "",
    val category: String = "All",
    val authorName: String = "",
    val authorImageUrl: String? = null,
    val timestamp: Timestamp = Timestamp.now()
)
