package com.fadali.flod_restaurant.core.component.bottomnavbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.presentation.ui.theme.navBarBorder

/**
 * Clean and reusable BottomNavBar composable following Material Design 3 guidelines
 * 
 * @param navController Navigation controller for handling navigation
 * @param items List of navigation items to display
 * @param modifier Optional modifier for styling
 * @param containerColor Background color of the navigation bar
 * @param contentColor Color for icons and text
 */
@Composable
fun BottomNavBar(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val cornerRadius = 6.dp

    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.navBarBorder,
                shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)
            ),
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        items.forEach { item ->
            val isSelected = currentDestination?.route == item.graph.startDestination::class.qualifiedName

            NavigationBarItem(
                icon = {
                    BottomNavIcon(item = item)
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.graph.startDestination) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@Composable
private fun BottomNavIcon(
    item: BottomNavItem
) {
    BadgedBox(
        badge = {
            if (item.hasNotification) {
                Badge(
                    modifier = Modifier.padding(start = 2.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = item.notificationText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(item.icon),
            contentDescription = stringResource(item.label)
        )
    }
}
