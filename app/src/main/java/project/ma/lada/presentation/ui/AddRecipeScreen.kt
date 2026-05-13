package project.ma.lada.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import project.ma.lada.R
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.model.User
import project.ma.lada.presentation.viewmodel.RecipeViewModel
import project.ma.lada.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
        user: User?,
        onBackClick: () -> Unit,
        onRecipeSaved: () -> Unit,
        viewModel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory),
        modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        Column(
                modifier =
                        Modifier.padding(padding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .imePadding()
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                    )
                }
                Text(
                        text = "Add Recipe",
                        fontSize = 22.sp,
                        fontFamily = poppinsBold,
                        color = Color.Black
                )
            }

            RecipeTextField(label = "Title", value = title, onValueChange = { title = it })
            RecipeTextField(
                    label = "Description",
                    value = description,
                    onValueChange = { description = it },
                    minLines = 3
            )
            RecipeTextField(
                    label = "Ingredients",
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    supportingText = "Separate each ingredient with a comma",
                    minLines = 2
            )
            RecipeTextField(
                    label = "Steps",
                    value = steps,
                    onValueChange = { steps = it },
                    supportingText = "Separate each step with a new line",
                    minLines = 4
            )
            RecipeTextField(label = "Time", value = time, onValueChange = { time = it })
            RecipeTextField(label = "Category", value = category, onValueChange = { category = it })
            RecipeTextField(
                    label = "Image URL",
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    keyboardType = KeyboardType.Uri
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                    onClick = {
                        val currentUser = user
                        when {
                            currentUser == null ->
                                    Toast.makeText(context, "Please sign in first", Toast.LENGTH_SHORT)
                                            .show()
                            title.isBlank() || ingredients.isBlank() || steps.isBlank() ->
                                    Toast.makeText(
                                                    context,
                                                    "Title, ingredients, and steps are required",
                                                    Toast.LENGTH_SHORT
                                            )
                                            .show()
                            else -> {
                                isSaving = true
                                val recipe =
                                        Recipe(
                                                title = title.trim(),
                                                description = description.trim(),
                                                ingredients =
                                                        ingredients.split(",")
                                                                .map { it.trim() }
                                                                .filter { it.isNotEmpty() },
                                                steps =
                                                        steps.lines()
                                                                .map { it.trim() }
                                                                .filter { it.isNotEmpty() },
                                                imageUrl = imageUrl.trim().ifEmpty { null },
                                                userId = currentUser.uid,
                                                time = time.trim(),
                                                category = category.trim().ifEmpty { "All" },
                                                authorName =
                                                        currentUser.displayName
                                                                ?: currentUser.email
                                                                ?: "Chef",
                                                authorImageUrl = currentUser.photoUrl
                                        )
                                viewModel.addRecipe(recipe) { result ->
                                    isSaving = false
                                    if (result.isSuccess) {
                                        Toast.makeText(context, "Recipe saved", Toast.LENGTH_SHORT)
                                                .show()
                                        onRecipeSaved()
                                    } else {
                                        Toast.makeText(
                                                        context,
                                                        result.exceptionOrNull()?.message
                                                                ?: "Failed to save recipe",
                                                        Toast.LENGTH_SHORT
                                                )
                                                .show()
                                    }
                                }
                            }
                        }
                    },
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text(
                        text = if (isSaving) "Saving..." else "Save Recipe",
                        fontFamily = poppinsBold,
                        fontSize = 14.sp,
                        color = Color.White
                )
            }

            Text(
                    text = "Recipes are saved to Firestore and appear in the home feed.",
                    fontSize = 12.sp,
                    fontFamily = poppins,
                    color = Color.Gray
            )
        }
    }
}

@Composable
private fun RecipeTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        supportingText: String? = null,
        minLines: Int = 1,
        keyboardType: KeyboardType = KeyboardType.Text
) {
    val poppins = FontFamily(Font(R.font.poppins))

    OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, fontFamily = poppins) },
            supportingText = supportingText?.let { { Text(text = it, fontFamily = poppins) } },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = minLines,
            keyboardOptions =
                    KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = keyboardType
                    ),
            colors =
                    OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = primary,
                            unfocusedBorderColor = Color.LightGray
                    )
    )
}
