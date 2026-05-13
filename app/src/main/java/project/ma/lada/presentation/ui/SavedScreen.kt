package project.ma.lada.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ma.lada.R
import project.ma.lada.domain.model.Recipe
import project.ma.lada.presentation.components.ProfileSavedRecipeCard
import project.ma.lada.ui.theme.primary

@Composable
fun SavedScreen(
        recipes: List<Recipe>,
        onRecipeClick: (String) -> Unit,
        onRemoveSaved: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding =
                        androidx.compose.foundation.layout.PaddingValues(
                                start = 20.dp,
                                top = 20.dp,
                                end = 20.dp,
                                bottom = 120.dp
                        ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SavedTitle() }
            if (recipes.isEmpty()) {
                item { SavedEmptyState(modifier = Modifier.fillParentMaxHeight(0.75f)) }
            } else {
                items(recipes) { recipe ->
                    ProfileSavedRecipeCard(
                            title = recipe.title,
                            author = recipe.authorName.ifEmpty { "Chef" },
                            time = recipe.time,
                            rating = recipe.rating,
                            imageUrl = recipe.imageUrl,
                            onCardClick = { onRecipeClick(recipe.id) },
                            onBookmarkClick = { onRemoveSaved(recipe.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedTitle() {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Text(text = "Saved Recipes", fontSize = 22.sp, fontFamily = poppinsBold, color = Color.Black)
}

@Composable
private fun SavedEmptyState(modifier: Modifier = Modifier) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                    tint = primary,
                    modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                    text = "No saved recipes yet",
                    fontSize = 16.sp,
                    fontFamily = poppinsBold,
                    color = Color.Black
            )
            Text(
                    text = "Recipes you save will appear here.",
                    fontSize = 12.sp,
                    fontFamily = poppins,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
            )
        }
    }
}
