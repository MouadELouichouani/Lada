package project.ma.lada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import project.ma.lada.ui.theme.LadaTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.ma.lada.presentation.navigation.Screen
import project.ma.lada.presentation.ui.SignInScreen
import project.ma.lada.presentation.ui.SplashScreen
import project.ma.lada.LadaApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LadaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onStartClick = { navController.navigate(Screen.SignIn.route) }
                            )
                        }
                        composable(Screen.SignIn.route) {
                            SignInScreen(
                                onSignInClick = { /* TODO: navigation to Home? */ }
                            )
                        }
                    }
                }
            }
        }
    }
}
