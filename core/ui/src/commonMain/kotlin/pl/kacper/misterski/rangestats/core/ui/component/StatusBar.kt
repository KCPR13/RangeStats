package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.Spacing
import pl.kacper.misterski.rangestats.core.ui.theme.Spacing.dp20
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted

//TODO HARDCODED
// Status bar for use in @Preview only — shows time and signal indicators
@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dp20, vertical = Spacing.dp4),
    ) {
        Text(
            text = "9:41",
            color = TacTextMuted,
            fontSize = 12.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "LTE",
            color = TacTextMuted,
            fontSize = 12.sp,
        )
    }
}

@Preview
@Composable
private fun StatusBarPreview() {
    RangeStatsTheme {
        StatusBar()
    }
}
