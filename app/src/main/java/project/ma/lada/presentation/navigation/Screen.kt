package project.ma.lada.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object SignIn : Screen("sign_in_screen")
}
