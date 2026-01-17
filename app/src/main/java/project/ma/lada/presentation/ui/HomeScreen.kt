package project.ma.lada.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ma.lada.R
import project.ma.lada.presentation.components.*
import project.ma.lada.presentation.viewmodel.AuthUiState
import project.ma.lada.presentation.viewmodel.RecipeUiState

@Composable
fun HomeScreen(
        authUiState: AuthUiState,
        recipeUiState: RecipeUiState,
        modifier: Modifier = Modifier
) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val categories = listOf("All", "Moroccan", "Italian", "Asian", "Chinese")
    var selectedCategory by remember { mutableStateOf("All") }
    var searchEffect by remember { mutableStateOf("") }

    val user = if (authUiState is AuthUiState.Success) authUiState.user else null
    val recipes = if (recipeUiState is RecipeUiState.Success) recipeUiState.recipes else emptyList()

    val filteredRecipes =
            recipes.filter {
                (selectedCategory == "All" || it.category == selectedCategory) &&
                        (it.title.contains(searchEffect, ignoreCase = true))
            }

    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            item {
                val formattedName = user?.displayName?.trim()?.split(" ")?.lastOrNull() ?: "Guest"
                HomeHeader(name = formattedName, profilePicUrl = user?.photoUrl)
            }

            item { SearchBar(onSearch = { searchEffect = it }, onFilterClick = { /* TODO */}) }

            item {
                CategoryTabs(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                )
            }

            item {
                if (recipeUiState is RecipeUiState.Loading) {
                    HomeSkeleton()
                } else {
                    LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        items(filteredRecipes) { recipe ->
                            FoodCard(
                                    title = recipe.title,
                                    time = recipe.time,
                                    rating = recipe.rating.toString(),
                                    imageRes =
                                            R.drawable
                                                    .splash_pic, // Placeholder since we don't have
                                    // images yet
                                    onCardClick = { /* TODO */},
                                    onBookmarkClick = { /* TODO */}
                            )
                        }
                    }
                }
            }

            item {
                Text(
                        text = "New Recipes",
                        fontSize = 18.sp,
                        fontFamily = poppinsBold,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        color = Color.Black
                )
            }

            item {
                if (recipeUiState is RecipeUiState.Success) {
                    LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        items(filteredRecipes.takeLast(5)) { recipe ->
                            RecentRecipeCard(
                                    title = recipe.title,
                                    author = recipe.authorName,
                                    time = recipe.time,
                                    rating = recipe.rating.toInt(),
                                    imageRes = R.drawable.splash_pic, // Placeholder
                                    authorImageRes = R.drawable.splash_pic, // Placeholder
                                    onCardClick = { /* TODO */}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeSkeleton() {
    LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(3) {
            Column(
                    modifier =
                            Modifier.width(150.dp)
                                    .background(Color.White, RoundedCornerShape(16.dp))
                                    .padding(8.dp)
            ) {
                // Image
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(150.dp)
                                        .shimmerEffect()
                                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Title
                Box(modifier = Modifier.fillMaxWidth(0.8f).height(16.dp).shimmerEffect())
                Spacer(modifier = Modifier.height(8.dp))
                // Bottom row (time/rating)
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.width(40.dp).height(12.dp).shimmerEffect())
                    Box(modifier = Modifier.width(30.dp).height(12.dp).shimmerEffect())
                }
            }
        }
    }
}
