package pl.kacper.misterski.rangestats.core.ui.component.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted

@Composable
fun TacSegmentedToggle(
    options: List<TacSegmentedToggleOptionUiModel>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    fullWidth: Boolean = false,
) {
    Row(
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .background(TacBgElevated, PrecisionTrackShapes.small),
    ) {
        options.forEachIndexed { index, option ->
            TacSegmentedToggleOption(
                label = option.label,
                selected = option.selected,
                onClick = { onSelect(index) },
                modifier = if (fullWidth) Modifier.weight(1f) else Modifier,
            )
        }
    }
}

@Composable
private fun TacSegmentedToggleOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(PrecisionTrackShapes.small)
            .background(if (selected) TacAccent else TacBgElevated)
            .clickable(onClick = onClick)
            .padding(horizontal = Dimen.dp12, vertical = Dimen.dp4),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = if (selected) TacOnAccent else TacTextMuted,
            fontSize = FontSize.sp12,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun TacSegmentedToggleCompactPreview() {
    RangeStatsTheme {
        TacSegmentedToggle(
            options = listOf(
                TacSegmentedToggleOptionUiModel(label = "G1", selected = true),
                TacSegmentedToggleOptionUiModel(label = "G7", selected = false),
            ),
            onSelect = {},
        )
    }
}

@Preview
@Composable
private fun TacSegmentedToggleFullWidthPreview() {
    RangeStatsTheme {
        TacSegmentedToggle(
            options = listOf(
                TacSegmentedToggleOptionUiModel(label = "Metric", selected = true),
                TacSegmentedToggleOptionUiModel(label = "Imperial", selected = false),
            ),
            onSelect = {},
            fullWidth = true,
        )
    }
}
