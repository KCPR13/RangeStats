package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme

@Composable
fun App() {
    RangeStatsTheme {
        TacScaffold {

        }

    }
}

@Preview
@Composable
private fun AppPreview() {
    RangeStatsTheme {
        App()
    }
}
