package project.ma.lada.presentation.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ma.lada.R
import project.ma.lada.ui.theme.primary
import project.ma.lada.ui.theme.teal
import project.ma.lada.ui.theme.warnings

private data class NotificationItem(
        val title: String,
        val message: String,
        val time: String,
        val icon: ImageVector,
        val color: Color
)

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    val notifications =
            remember {
                listOf(
                        NotificationItem(
                                title = "New follower",
                                message = "A chef started following your profile.",
                                time = "2 min ago",
                                icon = Icons.Default.PersonAdd,
                                color = teal
                        ),
                        NotificationItem(
                                title = "Recipe liked",
                                message = "Your latest recipe received a new like.",
                                time = "18 min ago",
                                icon = Icons.Default.Favorite,
                                color = warnings
                        ),
                        NotificationItem(
                                title = "Fresh recipes",
                                message = "New dishes are ready to explore in your feed.",
                                time = "Today",
                                icon = Icons.Default.Restaurant,
                                color = primary
                        )
                )
            }

    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.White) { padding ->
        LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { ScreenTitle(title = "Notifications") }
            items(notifications) { notification -> NotificationRow(notification = notification) }
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Text(text = title, fontSize = 22.sp, fontFamily = poppinsBold, color = Color.Black)
}

@Composable
private fun NotificationRow(notification: NotificationItem) {
    val poppins = FontFamily(Font(R.font.poppins))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

    Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF8F8F8)
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                    modifier =
                            Modifier.size(46.dp)
                                    .background(notification.color.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        imageVector = notification.icon,
                        contentDescription = null,
                        tint = notification.color,
                        modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(
                        text = notification.title,
                        fontSize = 14.sp,
                        fontFamily = poppinsBold,
                        color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = notification.message,
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        color = Color(0xFF707070),
                        lineHeight = 16.sp
                )
            }

            Text(text = notification.time, fontSize = 11.sp, fontFamily = poppins, color = Color.Gray)
        }
    }
}
