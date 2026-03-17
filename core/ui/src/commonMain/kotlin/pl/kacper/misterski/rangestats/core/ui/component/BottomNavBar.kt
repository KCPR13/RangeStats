package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import rangestats.core.ui.generated.resources.Res
import rangestats.core.ui.generated.resources.nav_ballistics
import rangestats.core.ui.generated.resources.nav_history
import rangestats.core.ui.generated.resources.nav_home
import rangestats.core.ui.generated.resources.nav_new_session
import rangestats.core.ui.generated.resources.nav_settings

@Composable
fun BottomNavBar(
    selected: BottomNavDestination,
    onNavigate: (BottomNavDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    NavigationBar(
        modifier = modifier,
        containerColor = TacBgPanel,
        tonalElevation = 0.dp,
    ) {
        NavigationBarItem(
            selected = selected == BottomNavDestination.Home,
            onClick = { onNavigate(BottomNavDestination.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = if (isPreview) "Home" else stringResource(Res.string.nav_home)) },
            label = { Text(if (isPreview) "Home" else stringResource(Res.string.nav_home)) },
            colors = navItemColors(),
        )
        NavigationBarItem(
            selected = selected == BottomNavDestination.History,
            onClick = { onNavigate(BottomNavDestination.History) },
            icon = { Icon(Icons.Default.History, contentDescription = if (isPreview) "History" else stringResource(Res.string.nav_history)) },
            label = { Text(if (isPreview) "History" else stringResource(Res.string.nav_history)) },
            colors = navItemColors(),
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
                        Icon(Icons.Default.Add, contentDescription = if (isPreview) "New session" else stringResource(Res.string.nav_new_session))
                    }
                }
            },
            label = { Text("") },
            colors = navItemColors(),
        )
        NavigationBarItem(
            selected = selected == BottomNavDestination.Ballistics,
            onClick = { onNavigate(BottomNavDestination.Ballistics) },
            icon = { Icon(Icons.Default.Calculate, contentDescription = if (isPreview) "Ballistics" else stringResource(Res.string.nav_ballistics)) },
            label = { Text(if (isPreview) "Ballistics" else stringResource(Res.string.nav_ballistics)) },
            colors = navItemColors(),
        )
        NavigationBarItem(
            selected = selected == BottomNavDestination.Settings,
            onClick = { onNavigate(BottomNavDestination.Settings) },
            icon = { Icon(Icons.Default.Settings, contentDescription = if (isPreview) "Settings" else stringResource(Res.string.nav_settings)) },
            label = { Text(if (isPreview) "Settings" else stringResource(Res.string.nav_settings)) },
            colors = navItemColors(),
        )
    }
}

@Composable
private fun navItemColors() = NavigationBarItemDefaults.colors(
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
