package project.ma.lada.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import project.ma.lada.R
import project.ma.lada.ui.theme.primary

@Composable
fun HomeHeader(
    name: String,
    profilePicUrl: String?,
    modifier: Modifier = Modifier
) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hello $name",
                fontSize = 20.sp,
                fontFamily = poppinsBold,
                color = Color.Black
            )
            Text(
                text = "What are you cooking today?",
                fontSize = 12.sp,
                fontFamily = poppins,
                color = Color.Gray
            )
        }

        Surface(
            modifier = Modifier.size(44.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFE5CCB5) // Placeholder background
        ) {
            if (profilePicUrl != null) {
                AsyncImage(
                    model = profilePicUrl,
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.splash_pic),
                    contentDescription = "Profile Placeholder",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val poppins = FontFamily(Font(R.font.poppins))
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { 
                query = it
                onSearch(it)
            },
            placeholder = {
                Text(
                    text = "Search recipe",
                    color = Color.LightGray,
                    fontFamily = poppins,
                    fontSize = 11.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(12.dp))

        Surface(
            onClick = onFilterClick,
            modifier = Modifier.size(44.dp),
            shape = RoundedCornerShape(12.dp),
            color = primary
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val poppins = FontFamily(Font(R.font.poppins))

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Surface(
                onClick = { onCategorySelected(category) },
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) primary else Color.Transparent,
                modifier = Modifier.height(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else primary,
                        fontSize = 12.sp,
                        fontFamily = if (isSelected) poppinsBold else poppins
                    )
                }
            }
        }
    }
}
