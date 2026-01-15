package project.ma.lada.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import project.ma.lada.R
import project.ma.lada.ui.theme.primary

@Composable
fun ProfileSavedRecipeCard(
        title: String,
        author: String,
        time: String,
        rating: Double,
        imageUrl: String?,
        isBookmarked: Boolean = true,
        onCardClick: () -> Unit = {},
        onBookmarkClick: () -> Unit = {},
        modifier: Modifier = Modifier
) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Card(
            onClick = onCardClick,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier.fillMaxWidth().height(180.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (imageUrl != null) {
                AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                        painter = painterResource(id = R.drawable.splash_pic), // Fallback
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                    modifier =
                            Modifier.fillMaxSize()
                                    .background(
                                            Brush.verticalGradient(
                                                    colors =
                                                            listOf(
                                                                    Color.Transparent,
                                                                    Color.Black.copy(alpha = 0.8f)
                                                            ),
                                                    startY = 100f
                                            )
                                    )
            )

            Surface(
                    color = Color(0xFFE1F1EE),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
            ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFF129575), 
                            modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                            text = rating.toString(),
                            fontSize = 11.sp,
                            fontFamily = poppinsBold,
                            color = Color.Black
                    )
                }
            }

            Column(modifier = Modifier.align(Alignment.BottomStart).padding(12.dp).fillMaxWidth()) {
                Text(
                        text = title,
                        fontSize = 14.sp,
                        fontFamily = poppinsBold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth(0.7f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                        text = "By $author",
                        fontSize = 10.sp,
                        fontFamily = poppins,
                        color = Color(0xFFD9D9D9),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                )

                Spacer(
                        modifier = Modifier.height(8.dp)
                )

                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = "Time",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                                text = time,
                                fontSize = 11.sp,
                                fontFamily = poppins,
                                color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(32.dp),
                            onClick = onBookmarkClick
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                    imageVector =
                                            Icons.Outlined.BookmarkBorder, // Or Filled if saved
                                    contentDescription = "Bookmark",
                                    tint = primary,
                                    modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSavedRecipeCardPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        ProfileSavedRecipeCard(
                title = "Steak with tomato sauce and bulgur rice",
                author = "Chef Mouad",
                time = "20 min",
                rating = 4.0,
                imageUrl = null
        )
    }
}
