package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

@Composable
fun TacCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = PrecisionTrackShapes.medium,
            colors =
                CardDefaults.cardColors(
                    containerColor = TacBgCard,
                    contentColor = TacTextPrimary,
                ),
            border = BorderStroke(Dimen.dp1, TacBorder),
            content = content,
        )
    } else {
        Card(
            modifier = modifier,
            shape = PrecisionTrackShapes.medium,
            colors =
                CardDefaults.cardColors(
                    containerColor = TacBgCard,
                    contentColor = TacTextPrimary,
                ),
            border = BorderStroke(Dimen.dp1, TacBorder),
            content = content,
        )
    }
}

@Preview
@Composable
private fun TacCardPreview() {
    RangeStatsTheme {
        TacCard {
            Text("Zawartość karty")
        }
    }
}

@Preview
@Composable
private fun TacCardClickablePreview() {
    RangeStatsTheme {
        TacCard(onClick = {}) {
            Text("Klikalna karta")
        }
    }
}
