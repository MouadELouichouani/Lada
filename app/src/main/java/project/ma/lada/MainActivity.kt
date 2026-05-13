package project.ma.lada

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import project.ma.lada.presentation.components.CustomBottomNavigation
import project.ma.lada.presentation.navigation.Screen
import project.ma.lada.presentation.ui.AddRecipeScreen
import project.ma.lada.presentation.ui.HomeScreen
import project.ma.lada.presentation.ui.NotificationsScreen
import project.ma.lada.presentation.ui.ProfileScreen
import project.ma.lada.presentation.ui.RecipeDetailScreen
import project.ma.lada.presentation.ui.SavedScreen
import project.ma.lada.presentation.ui.SignInScreen
import project.ma.lada.presentation.ui.SignUpScreen
import project.ma.lada.presentation.ui.SplashScreen
import project.ma.lada.presentation.viewmodel.AuthUiState
import project.ma.lada.presentation.viewmodel.AuthViewModel
import project.ma.lada.presentation.viewmodel.RecipeUiState
import project.ma.lada.presentation.viewmodel.RecipeViewModel
import project.ma.lada.ui.theme.LadaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LadaTheme {
                val context = LocalContext.current
                val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
                val authUiState by authViewModel.uiState.collectAsState()

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val sharedPreferences = remember {
                    context.getSharedPreferences("lada_prefs", android.content.Context.MODE_PRIVATE)
                }
                val hasSeenSplash = remember {
                    sharedPreferences.getBoolean("has_seen_splash", false)
                }
                val savedRecipeIds =
                        remember {
                            mutableStateListOf<String>().apply {
                                addAll(
                                        sharedPreferences.getStringSet(
                                                        "saved_recipe_ids",
                                                        emptySet()
                                                ) ?: emptySet()
                                )
                            }
                        }

                val startDestination =
                        remember(authUiState) {
                            when (authUiState) {
                                is AuthUiState.Success -> Screen.Home.route
                                else ->
                                        if (hasSeenSplash) Screen.SignIn.route
                                        else Screen.Splash.route
                            }
                        }

                val showBottomBar =
                        currentRoute in
                                listOf(
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
                                        onAddClick = { navController.navigate(Screen.AddRecipe.route) }
                                )
                            }
                        }
                ) { innerPadding ->
                    NavHost(
                            navController = navController,
                            startDestination = startDestination,
                            modifier = Modifier.padding(innerPadding),
                            enterTransition = {
                                slideInHorizontally(
                                        initialOffsetX = { 1000 },
                                        animationSpec = tween(700)
                                ) + fadeIn(animationSpec = tween(700))
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                        targetOffsetX = { -1000 },
                                        animationSpec = tween(700)
                                ) + fadeOut(animationSpec = tween(700))
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                        initialOffsetX = { -1000 },
                                        animationSpec = tween(700)
                                ) + fadeIn(animationSpec = tween(700))
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                        targetOffsetX = { 1000 },
                                        animationSpec = tween(700)
                                ) + fadeOut(animationSpec = tween(700))
                            }
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
                            val authViewModel: AuthViewModel =
                                    viewModel(factory = AuthViewModel.Factory)
                            val recipeViewModel: RecipeViewModel =
                                    viewModel(factory = RecipeViewModel.Factory)

                            val authUiState by authViewModel.uiState.collectAsState()
                            val recipeUiState by recipeViewModel.recipeUiState.collectAsState()

                            HomeScreen(
                                    authUiState = authUiState,
                                    recipeUiState = recipeUiState,
                                    onRecipeClick = {
                                        navController.navigate(Screen.RecipeDetail.createRoute(it))
                                    },
                                    onBookmarkClick = { recipeId ->
                                        if (savedRecipeIds.contains(recipeId)) {
                                            savedRecipeIds.remove(recipeId)
                                            Toast.makeText(
                                                            context,
                                                            "Removed from saved recipes",
                                                            Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                        } else {
                                            savedRecipeIds.add(recipeId)
                                            Toast.makeText(
                                                            context,
                                                            "Recipe saved",
                                                            Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                        }
                                        sharedPreferences
                                                .edit()
                                                .putStringSet(
                                                        "saved_recipe_ids",
                                                        savedRecipeIds.toSet()
                                                )
                                                .apply()
                                    },
                                    onFilterClick = {
                                        Toast.makeText(
                                                        context,
                                                        "Use categories or search to filter recipes",
                                                        Toast.LENGTH_SHORT
                                                )
                                                .show()
                                    }
                            )
                        }
                        composable(Screen.AddRecipe.route) {
                            val authViewModel: AuthViewModel =
                                    viewModel(factory = AuthViewModel.Factory)
                            val authUiState by authViewModel.uiState.collectAsState()
                            val user =
                                    (authUiState as? AuthUiState.Success)?.user

                            AddRecipeScreen(
                                    user = user,
                                    onBackClick = { navController.popBackStack() },
                                    onRecipeSaved = {
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.AddRecipe.route) { inclusive = true }
                                        }
                                    }
                            )
                        }
                        composable(
                                route = Screen.RecipeDetail.route,
                                arguments =
                                        listOf(navArgument("recipeId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val recipeViewModel: RecipeViewModel =
                                    viewModel(factory = RecipeViewModel.Factory)
                            val recipeUiState by recipeViewModel.recipeUiState.collectAsState()
                            val recipeId = backStackEntry.arguments?.getString("recipeId")
                            val recipe =
                                    (recipeUiState as? RecipeUiState.Success)
                                            ?.recipes
                                            ?.firstOrNull { it.id == recipeId }

                            RecipeDetailScreen(
                                    recipe = recipe,
                                    onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.Saved.route) {
                            val recipeViewModel: RecipeViewModel =
                                    viewModel(factory = RecipeViewModel.Factory)
                            val recipeUiState by recipeViewModel.recipeUiState.collectAsState()
                            val savedRecipes =
                                    (recipeUiState as? RecipeUiState.Success)
                                            ?.recipes
                                            ?.filter { savedRecipeIds.contains(it.id) }
                                            ?: emptyList()

                            SavedScreen(
                                    recipes = savedRecipes,
                                    onRecipeClick = {
                                        navController.navigate(Screen.RecipeDetail.createRoute(it))
                                    },
                                    onRemoveSaved = { recipeId ->
                                        savedRecipeIds.remove(recipeId)
                                        sharedPreferences
                                                .edit()
                                                .putStringSet(
                                                        "saved_recipe_ids",
                                                        savedRecipeIds.toSet()
                                                )
                                                .apply()
                                        Toast.makeText(
                                                        context,
                                                        "Removed from saved recipes",
                                                        Toast.LENGTH_SHORT
                                                )
                                                .show()
                                    }
                            )
                        }
                        composable(Screen.Notifications.route) { NotificationsScreen() }
                        composable(Screen.Profile.route) {
                            val authViewModel: AuthViewModel =
                                    viewModel(factory = AuthViewModel.Factory)

                            ProfileScreen(
                                    onSignOut = {
                                        authViewModel.signOut()
                                        navController.navigate(Screen.SignIn.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                inclusive = true
                                            }
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
