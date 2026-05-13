package project.ma.lada.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import project.ma.lada.ui.theme.primary

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        Column(
                modifier =
                        Modifier.padding(padding)
                                .fillMaxSize()
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 120.dp)
        ) {
            SavedTitle()
            SavedEmptyState(modifier = Modifier.weight(1f))
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
