package project.ma.lada.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import project.ma.lada.R
import project.ma.lada.domain.model.Recipe
import project.ma.lada.domain.model.UserProfile
import project.ma.lada.presentation.components.ProfileSavedRecipeCard
import project.ma.lada.presentation.viewmodel.ProfileUiState
import project.ma.lada.presentation.viewmodel.ProfileViewModel
import project.ma.lada.ui.theme.primary

@Composable
fun ProfileScreen(
        viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
        modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load current user profile on start
    // In a real app with navigation arguments, we'd pass the UID here
    LaunchedEffect(Unit) { viewModel.loadProfile() }

    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = primary)
                    }
                }
                is ProfileUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = Color.Red)
                    }
                }
                is ProfileUiState.Success -> {
                    ProfileContent(
                            userProfile = state.userProfile,
                            recipes = state.recipes,
                            isCurrentUser = state.isCurrentUser,
                            onFollowClick = { viewModel.toggleFollow(state.userProfile.uid) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
        userProfile: UserProfile,
        recipes: List<Recipe>,
        isCurrentUser: Boolean,
        onFollowClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Recipe") }
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom nav
    ) {
        // Header
        item { ProfileHeader(userProfile = userProfile, onMoreClick = { /* TODO */}) }

        // Bio
        item { ProfileBio(userProfile = userProfile) }

        // Tabs
        item { ProfileTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) }

        // Content
        if (selectedTab == "Recipe") {
            items(recipes) { recipe ->
                ProfileSavedRecipeCard(
                        title = recipe.title,
                        author = userProfile.displayName.ifEmpty { "Chef" },
                        time = recipe.time,
                        rating = recipe.rating,
                        imageUrl = recipe.imageUrl,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
        } else {
            item {
                Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                ) { Text(text = "No $selectedTab yet", fontFamily = poppins, color = Color.Gray) }
            }
        }
    }
}

@Composable
fun ProfileHeader(userProfile: UserProfile, onMoreClick: () -> Unit) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        // Top Bar
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(24.dp)) // Balance centering
            Text(text = "Profile", fontSize = 18.sp, fontFamily = poppinsBold, color = Color.Black)
            IconButton(onClick = onMoreClick) {
                Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "More",
                        tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Image and Stats
        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile Image
            Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(100.dp),
                    color = Color.LightGray
            ) {
                if (userProfile.photoUrl != null) {
                    AsyncImage(
                            model = userProfile.photoUrl,
                            contentDescription = "Profile Pic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                            painter = painterResource(id = R.drawable.splash_pic), // Fallback
                            contentDescription = "Profile Placeholder",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Stats
            Row(
                    modifier = Modifier.weight(1f).padding(start = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProfileStatItem("Recipe", userProfile.recipeCount.toString())
                ProfileStatItem("Followers", formatCount(userProfile.followers.size))
                ProfileStatItem(
                        "Following",
                        userProfile.following.size.toString()
                ) // Usually raw number is fine for following
            }
        }
    }
}

@Composable
fun ProfileStatItem(label: String, value: String) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 12.sp, fontFamily = poppins, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 20.sp, fontFamily = poppinsBold, color = Color.Black)
    }
}

// Helper to format 2500 -> 2.5K
fun formatCount(count: Int): String {
    if (count < 1000) return count.toString()
    val k = count / 1000.0
    return String.format("%.1fM", k)
            .replace(".0M", "M") // Just a simple placeholder logic for "2.5M" style from design
}

@Composable
fun ProfileBio(userProfile: UserProfile) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Text(
                text = userProfile.displayName.ifEmpty { "Unknown User" },
                fontSize = 18.sp,
                fontFamily = poppinsBold,
                color = Color.Black
        )

        Text(text = userProfile.role, fontSize = 12.sp, fontFamily = poppins, color = Color.Gray)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
                text =
                        userProfile.bio.ifEmpty {
                            "Private Chef\nPassionate about food and life \uD83C\uDF73\uD83C\uDF5B\uD83C\uDF5D\uD83C\uDF71"
                        }, // Fallback from design
                fontSize = 12.sp,
                fontFamily = poppins,
                color = Color(0xFF484848),
                lineHeight = 18.sp
        )

        Text(
                text = "More...",
                fontSize = 12.sp,
                fontFamily = poppins,
                color = primary,
                modifier = Modifier.padding(top = 4.dp).clickable { /* Expand Bio */}
        )
    }
}

@Composable
fun ProfileTabs(selectedTab: String, onTabSelected: (String) -> Unit) {
    val tabs = listOf("Recipe", "Videos", "Tag")
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Button(
                    onClick = { onTabSelected(tab) },
                    colors =
                            ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) primary else Color.Transparent,
                                    contentColor = if (isSelected) Color.White else primary
                            ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                    elevation = null,
                    border =
                            if (isSelected) null
                            else null // Design doesn't show border for unselected, just text
            ) {
                Text(
                        text = tab,
                        fontFamily = if (isSelected) poppinsBold else poppins,
                        fontSize = 12.sp
                )
            }
        }
    }
}
