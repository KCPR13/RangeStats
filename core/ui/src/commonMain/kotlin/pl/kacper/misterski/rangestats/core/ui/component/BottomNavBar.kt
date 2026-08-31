package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted

@Composable
fun BottomNavBar(
    selected: BottomNavDestination,
    onNavigate: (BottomNavDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier = modifier,
        containerColor = TacBgPanel,
        tonalElevation = 0.dp,
    ) {
        NavItem(
            destination = BottomNavDestination.Home,
            selected = selected,
            onNavigate = onNavigate,
            icon = Icons.Default.Home,
        )
        NavItem(
            destination = BottomNavDestination.History,
            selected = selected,
            onNavigate = onNavigate,
            icon = Icons.Default.History,
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(BottomNavDestination.NewSession) },
            icon = {
                Box(
                    modifier = Modifier.size(Dimen.dp56),
                    contentAlignment = Alignment.Center,
                ) {
                    FloatingActionButton(
                        onClick = { onNavigate(BottomNavDestination.NewSession) },
                        modifier = Modifier.size(Dimen.dp48),
                        containerColor = TacAccent,
                        contentColor = TacOnAccent,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                }
            },
            label = { Text("") },
            colors = navItemColors(),
        )
        NavItem(
            destination = BottomNavDestination.Ballistics,
            selected = selected,
            onNavigate = onNavigate,
            icon = Icons.Default.Calculate,
        )
        NavItem(
            destination = BottomNavDestination.Settings,
            selected = selected,
            onNavigate = onNavigate,
            icon = Icons.Default.Settings,
        )
    }
}

@Composable
private fun RowScope.NavItem(
    destination: BottomNavDestination,
    selected: BottomNavDestination,
    onNavigate: (BottomNavDestination) -> Unit,
    icon: ImageVector,
) {
    NavigationBarItem(
        selected = selected == destination,
        onClick = { onNavigate(destination) },
        alwaysShowLabel = false,
        icon = { Icon(icon, contentDescription = null) },
        colors = navItemColors(),
    )
}

@Composable
private fun navItemColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = TacAccent,
        selectedTextColor = TacAccent,
        indicatorColor = Color.Transparent,
        unselectedIconColor = TacTextMuted,
        unselectedTextColor = TacTextMuted,
    )

@Preview
@Composable
private fun BottomNavBarHomePreview() {
    RangeStatsTheme {
        BottomNavBar(
            selected = BottomNavDestination.Home,
            onNavigate = {},
        )
    }
}

@Preview
@Composable
private fun BottomNavBarHistoryPreview() {
    RangeStatsTheme {
        BottomNavBar(
            selected = BottomNavDestination.History,
            onNavigate = {},
        )
    }
}
