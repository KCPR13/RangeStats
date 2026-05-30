package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep

@Composable
fun TacScaffold(
    modifier: Modifier = Modifier,
    selectedNav: BottomNavDestination? = null,
    onNavigate: ((BottomNavDestination) -> Unit)? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeGestures,
        modifier = modifier,
        containerColor = TacBgDeep,
        topBar = topBar,
        bottomBar = {
            if (selectedNav != null && onNavigate != null) {
                BottomNavBar(
                    selected = selectedNav,
                    onNavigate = onNavigate,
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview
@Composable
private fun TacScaffoldWithNavPreview() {
    RangeStatsTheme {
        TacScaffold(
            selectedNav = BottomNavDestination.Home,
            onNavigate = {},
        ) {
            Text("Zawartość ekranu")
        }
    }
}

@Preview
@Composable
private fun TacScaffoldNoNavPreview() {
    RangeStatsTheme {
        TacScaffold {
            Text("Ekran bez nawigacji")
        }
    }
}
