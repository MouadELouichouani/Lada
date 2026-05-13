package project.ma.lada.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object SignIn : Screen("sign_in_screen")
    object SignUp : Screen("sign_up_screen")
    object Home : Screen("home_screen")
    object Saved : Screen("saved_screen")
    object AddRecipe : Screen("add_recipe_screen")
    object RecipeDetail : Screen("recipe_detail_screen/{recipeId}") {
        fun createRoute(recipeId: String): String = "recipe_detail_screen/$recipeId"
    }
    object Notifications : Screen("notifications_screen")
    object Profile : Screen("profile_screen")
}
