package project.ma.lada.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import project.ma.lada.R
import project.ma.lada.presentation.navigation.Screen
import project.ma.lada.ui.theme.primary

@Composable
fun CustomBottomNavigation(
        currentRoute: String?,
        onNavigate: (String) -> Unit,
        onAddClick: () -> Unit
) {
        val navigationBarItems = remember {
                listOf(
                        NavigationBarItem(
                                id = Screen.Home.route,
                                icon = R.drawable.home,
                                contentDescription = "Home"
                        ),
                        NavigationBarItem(
                                id = Screen.Saved.route,
                                icon = R.drawable.saved_icon,
                                contentDescription = "Saved"
                        ),
                        NavigationBarItem(
                                id = "add_action",
                                icon = R.drawable.plus_icon,
                                contentDescription = "Add"
                        ),
                        NavigationBarItem(
                                id = Screen.Notifications.route,
                                icon = R.drawable.notification_icon,
                                contentDescription = "Notifications"
                        ),
                        NavigationBarItem(
                                id = Screen.Profile.route,
                                icon = R.drawable.profile_icon,
                                contentDescription = "Profile"
                        )
                )
        }

        val selectedIndex =
                remember(currentRoute) {
                        val index = navigationBarItems.indexOfFirst { it.id == currentRoute }
                        if (index != -1) index else 0
                }

        Box(
                modifier =
                        Modifier.background(Color.Transparent)
                                .navigationBarsPadding()
                                .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
        ) {
                AnimatedNavigationBar(
                        modifier =
                                Modifier.padding(horizontal = 8.dp, vertical = 20.dp).height(85.dp),
                        selectedIndex = selectedIndex,
                        cornerRadius = shapeCornerRadius(25.dp),
                        ballAnimation = Parabolic(tween(300)),
                        indentAnimation = Height(tween(300)),
                        barColor = Color.White,
                        ballColor = primary
                ) {
                        navigationBarItems.forEachIndexed { index, item ->
                                val isSelected = selectedIndex == index
                                Box(
                                        modifier =
                                                Modifier.fillMaxSize().clickable(
                                                                interactionSource =
                                                                        remember {
                                                                                MutableInteractionSource()
                                                                        },
                                                                indication = null
                                                        ) {
                                                        if (item.id == "add_action") {
                                                                onAddClick()
                                                        } else {
                                                                onNavigate(item.id)
                                                        }
                                                },
                                        contentAlignment = Alignment.Center
                                ) {
                                        androidx.compose.foundation.layout.Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                        ) {
                                                Icon(
                                                        painter = painterResource(id = item.icon),
                                                        contentDescription =
                                                                item.contentDescription,
                                                        tint =
                                                                if (isSelected) primary
                                                                else Color.LightGray,
                                                        modifier = Modifier.size(24.dp)
                                                )
                                        }
                                }
                        }
                }
        }
}

data class NavigationBarItem(val id: String, val icon: Int, val contentDescription: String)
