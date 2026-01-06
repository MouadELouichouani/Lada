package project.ma.lada.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import project.ma.lada.presentation.navigation.Screen
import project.ma.lada.ui.theme.primary

@Composable
fun CustomBottomNavigation(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier.height(80.dp)
        ) {
            NavigationBarItem(
                selected = currentRoute == Screen.Home.route,
                onClick = { onNavigate(Screen.Home.route) },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == Screen.Home.route) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Home",
                        tint = if (currentRoute == Screen.Home.route) primary else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = currentRoute == Screen.Saved.route,
                onClick = { onNavigate(Screen.Saved.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Saved",
                        tint = if (currentRoute == Screen.Saved.route) primary else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )

            Box(modifier = Modifier.weight(1f))

            NavigationBarItem(
                selected = currentRoute == Screen.Notifications.route,
                onClick = { onNavigate(Screen.Notifications.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = if (currentRoute == Screen.Notifications.route) primary else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = currentRoute == Screen.Profile.route,
                onClick = { onNavigate(Screen.Profile.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = if (currentRoute == Screen.Profile.route) primary else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }

        FloatingActionButton(
            onClick = onAddClick,
            shape = CircleShape,
            containerColor = primary,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            modifier = Modifier
                .offset(y = (-40).dp)
                .size(64.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
