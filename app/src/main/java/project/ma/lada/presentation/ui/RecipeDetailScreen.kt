package project.ma.lada.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import project.ma.lada.R
import project.ma.lada.domain.model.Recipe
import project.ma.lada.ui.theme.primary

@Composable
fun RecipeDetailScreen(recipe: Recipe?, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        Column(
                modifier =
                        Modifier.padding(padding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                    )
                }
                Text(text = "Recipe", fontSize = 22.sp, fontFamily = poppinsBold, color = Color.Black)
            }

            if (recipe == null) {
                Box(modifier = Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) {
                    Text(text = "Recipe not found", fontFamily = poppins, color = Color.Gray)
                }
            } else {
                RecipeHero(recipe = recipe)

                Text(
                        text = recipe.title,
                        fontSize = 24.sp,
                        fontFamily = poppinsBold,
                        color = Color.Black,
                        lineHeight = 30.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    RecipePill(icon = Icons.Outlined.Schedule, text = recipe.time.ifEmpty { "No time" })
                    RecipePill(icon = Icons.Default.Star, text = recipe.rating.toString())
                }

                if (recipe.description.isNotBlank()) {
                    Text(
                            text = recipe.description,
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            color = Color(0xFF484848),
                            lineHeight = 20.sp
                    )
                }

                RecipeSection(title = "Ingredients", items = recipe.ingredients)
                RecipeSection(title = "Steps", items = recipe.steps)
            }
        }
    }
}

@Composable
private fun RecipeHero(recipe: Recipe) {
    Box(
            modifier =
                    Modifier.fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(18.dp))
    ) {
        if (recipe.imageUrl != null) {
            AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                    painter = painterResource(id = R.drawable.splash_pic),
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun RecipePill(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    val poppins = FontFamily(Font(R.font.poppins))

    Surface(shape = RoundedCornerShape(100.dp), color = Color(0xFFF4F4F4)) {
        Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = primary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = text, fontFamily = poppins, fontSize = 12.sp, color = Color.Black)
        }
    }
}

@Composable
private fun RecipeSection(title: String, items: List<String>) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, fontSize = 18.sp, fontFamily = poppinsBold, color = Color.Black)
        if (items.isEmpty()) {
            Text(text = "Nothing added yet.", fontSize = 13.sp, fontFamily = poppins, color = Color.Gray)
        } else {
            items.forEachIndexed { index, item ->
                Text(
                        text = "${index + 1}. $item",
                        fontSize = 14.sp,
                        fontFamily = poppins,
                        color = Color(0xFF484848),
                        lineHeight = 20.sp
                )
            }
        }
    }
}
