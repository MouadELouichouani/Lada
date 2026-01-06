package project.ma.lada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import project.ma.lada.presentation.navigation.Screen
import project.ma.lada.presentation.ui.HomeScreen
import project.ma.lada.presentation.ui.SignInScreen
import project.ma.lada.presentation.ui.SignUpScreen
import project.ma.lada.presentation.ui.SplashScreen
import project.ma.lada.presentation.viewmodel.GreetingViewModel
import project.ma.lada.ui.theme.LadaTheme
import project.ma.lada.presentation.components.CustomBottomNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LadaTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Saved.route,
                    Screen.Notifications.route,
                    Screen.Profile.route
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            CustomBottomNavigation(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onAddClick = { /* TODO: Implement Add action */ }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onStartClick = { 
                                    navController.navigate(Screen.SignIn.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.SignIn.route) {
                            SignInScreen(
                                onLoginSuccess = { 
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.SignIn.route) { inclusive = true }
                                    }
                                },
                                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
                            )
                        }
                        composable(Screen.SignUp.route) {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.SignUp.route) { inclusive = true }
                                    }
                                },
                                onSignInClick = { navController.navigate(Screen.SignIn.route) }
                            )
                        }
                        composable(Screen.Home.route) {
                            val greetingViewModel: GreetingViewModel = viewModel(factory = GreetingViewModel.Factory)
                            val state by greetingViewModel.state.collectAsState()
                            HomeScreen(state = state)
                        }
                        composable(Screen.Saved.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "Saved Recipes Screen")
                            }
                        }
                        composable(Screen.Notifications.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "Notifications Screen")
                            }
                        }
                        composable(Screen.Profile.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "Profile Screen")
                            }
                        }
                    }
                }
            }
        }
    }
}
