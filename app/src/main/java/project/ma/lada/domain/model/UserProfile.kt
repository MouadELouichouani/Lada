package project.ma.lada.domain.model

data class UserProfile(
        val uid: String = "",
        val displayName: String = "",
        val photoUrl: String? = null,
        val bio: String = "",
        val role: String = "Chef",
        val followers: List<String> = emptyList(),
        val following: List<String> = emptyList(),
        val recipeCount: Int = 0
)
