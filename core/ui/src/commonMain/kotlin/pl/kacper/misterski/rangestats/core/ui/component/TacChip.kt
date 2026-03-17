package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccentDim
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary

@Composable
fun TacChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier.height(Dimen.dp32),
        shape = PrecisionTrackShapes.extraSmall,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = TacBgCard,
            labelColor = TacTextSecondary,
            selectedContainerColor = TacAccentDim,
            selectedLabelColor = TacOnAccent,
        ),
        border = BorderStroke(
            width = Dimen.dp1,
            color = if (selected) TacAccent else TacBorder,
        ),
    )
}

@Preview
@Composable
private fun TacChipSelectedPreview() {
    RangeStatsTheme {
        TacChip(label = "25m", selected = true, onClick = {})
    }
}

@Preview
@Composable
private fun TacChipUnselectedPreview() {
    RangeStatsTheme {
        TacChip(label = "50m", selected = false, onClick = {})
    }
}
