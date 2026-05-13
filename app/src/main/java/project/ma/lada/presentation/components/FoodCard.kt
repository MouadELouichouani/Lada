package project.ma.lada.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import project.ma.lada.R
import project.ma.lada.ui.theme.primary
import project.ma.lada.ui.theme.secondary

@Composable
fun FoodCard(
    title: String,
    time: String,
    rating: String,
    imageRes: Int,
    imageUrl: String? = null,
    onCardClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Box(
        modifier = modifier
            .width(160.dp)
            .height(240.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            onClick = onCardClick,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1)),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .padding(top = 40.dp) 
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontFamily = poppinsBold,
                    color = Color(0xFF484848),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Time",
                            fontSize = 10.sp,
                            fontFamily = poppins,
                            color = Color.Gray
                        )
                        Text(
                            text = time,
                            fontSize = 12.sp,
                            fontFamily = poppinsBold,
                            color = Color(0xFF484848)
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
                                imageVector = Icons.Outlined.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 0.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 0.dp)
                    .clip(CircleShape)
            )
        }

        Surface(
            color = Color(0xFFE1F1EE),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 4.dp, top = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = primary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = rating,
                    fontSize = 12.sp,
                    fontFamily = poppinsBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodCardPreview() {
    Box(modifier = Modifier.padding(20.dp)) {
        FoodCard(
            title = "Crunchy Nut Coleslaw",
            time = "10 Mins",
            rating = "3.5",
            imageRes = R.drawable.splash_pic
        )
    }
}
