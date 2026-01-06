package project.ma.lada.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object SignIn : Screen("sign_in_screen")
    object SignUp : Screen("sign_up_screen")
    object Home : Screen("home_screen")
    object Saved : Screen("saved_screen")
    object Notifications : Screen("notifications_screen")
    object Profile : Screen("profile_screen")
}
